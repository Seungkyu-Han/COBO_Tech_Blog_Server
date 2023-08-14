package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.Req.TechTechPostReq;
import cobo.blog.domain.Tech.Data.Dto.Res.TechImgRes;
import cobo.blog.domain.Tech.Data.Dto.Res.TechSkillTagRes;
import cobo.blog.domain.Tech.Data.Dto.Res.TechTechPostRes;
import cobo.blog.global.Data.Entity.FileEntity;
import cobo.blog.global.Data.Entity.SkillTagEntity;
import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Data.Entity.TechPostSkillTagMappingEntity;
import cobo.blog.global.Repository.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static cobo.blog.global.Util.PageRequestUtil.pageRequestGenerator;

@Service
@RequiredArgsConstructor
@Slf4j
@Component
public class TechServiceImpl {

    private final TechPostRepository techPostRepository;
    private final UserRepository userRepository;
    private final SkillTagRepository skillTagRepository;
    private final TechPostSkillTagMappingRepository techPostSkillTagMappingRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final FileRepository fileRepository;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.path}")
    private String path;
    @Value("${cloud.aws.s3.path-md}")
    private String pathMd;
    @Value("${cloud.aws.s3.path-img}")
    private String pathImg;
    private final String techPostRedisName = "techPost";
    public ResponseEntity<List<TechTechPostRes>> getPosts(Integer page, Integer size, Integer skillTagId) {
        List<TechTechPostRes> techTechPostRes = new ArrayList<>();
        PageRequest pageRequest = pageRequestGenerator(page, size, Sort.Direction.DESC, "id");
        for(TechPostEntity techPostEntity : (skillTagId == null) ?
                techPostRepository.findAll(pageRequest) : techPostRepository.getTechPostEntitiesBySkillTagId(skillTagId, pageRequest))
            techTechPostRes.add(new TechTechPostRes(techPostEntity, path + pathMd));
        return new ResponseEntity<>(techTechPostRes, HttpStatus.OK);
    }

    public ResponseEntity<Long> getTechCount(Integer skillTagId) {
        return (skillTagId == null) ?
                new ResponseEntity<>(techPostRepository.count(), HttpStatus.OK):
                new ResponseEntity<>(techPostRepository.countTechPostEntitiesBySkillTagId(skillTagId), HttpStatus.OK);
    }

    public ResponseEntity<List<TechSkillTagRes>> getSkillTags() {
        List<TechSkillTagRes> techSkillTagRes = new ArrayList<>();
        for(SkillTagEntity skillTagEntity : skillTagRepository.findAll())
            techSkillTagRes.add(new TechSkillTagRes(skillTagEntity));
        return new ResponseEntity<>(techSkillTagRes, HttpStatus.OK);
    }

    public ResponseEntity<TechTechPostRes> readPost(Integer techPostId) {
        redisTemplate.opsForValue().increment(techPostRedisName + techPostId);
        return new ResponseEntity<>(new TechTechPostRes(techPostRepository.findByTechPostId(techPostId), path + pathMd), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<HttpStatus> createPost(TechTechPostReq techTechPostReq, MultipartFile multipartFile) throws IOException{

        //S3에 데이터 업로드
        UUID uuidName = UUID.randomUUID();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        amazonS3Client.putObject(bucket,pathMd + uuidName, multipartFile.getInputStream(), metadata);

        //TechPost 데이터 생성
        TechPostEntity techPostEntity = new TechPostEntity
                (
                        techTechPostReq.getTitle(),
                        techTechPostReq.getContent(),
                        uuidName.toString(),
                        userRepository.getById(techTechPostReq.getUserId())
                );

        //Mapping 데이터 추가
        List<TechPostSkillTagMappingEntity> techPostSkillTagMappingEntities = skillTagMapping(techTechPostReq, techPostEntity);
        techPostEntity.setTechPostSkillTagMappings(techPostSkillTagMappingEntities);

        techPostRepository.save(techPostEntity);

        redisTemplate.opsForValue().set(techPostRedisName + techPostEntity.getId(), "0");

        //이미지와 글 mapping
        for(FileEntity fileEntity : fileRepository.findAllById(techPostRepository.getTechPostIdList())){
            fileEntity.setTechPost(techPostEntity);
            fileRepository.save(fileEntity);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<HttpStatus> updatePost(TechTechPostReq techTechPostReq, MultipartFile multipartFile) throws IOException{
        //Tech ID로 해당 TechPost 가져오기
        TechPostEntity techPostEntity = techPostRepository.findByTechPostId(techTechPostReq.getTechPostId());
        //techPostSkillTagRepository에 연관된 데이터 삭제해두기
        techPostSkillTagMappingRepository.deleteAllByTechPost(techPostEntity);
        //S3에 저장된 내용 지우기
        amazonS3Client.deleteObject(bucket, pathMd + techPostEntity.getFileName());

        //S3에 데이터 업로드
        UUID uuidName = UUID.randomUUID();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        amazonS3Client.putObject(bucket,pathMd + uuidName, multipartFile.getInputStream(), metadata);

        techPostEntity.UpdateByTechTechPostReqAndUrl(techTechPostReq,uuidName.toString());

        List<TechPostSkillTagMappingEntity> techPostSkillTagMappingEntities = skillTagMapping(techTechPostReq, techPostEntity);

        techPostSkillTagMappingRepository.saveAll(techPostSkillTagMappingEntities);
        techPostRepository.save(techPostEntity);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private List<TechPostSkillTagMappingEntity> skillTagMapping(TechTechPostReq techTechPostReq, TechPostEntity techPostEntity){
        List<TechPostSkillTagMappingEntity> techPostSkillTagMappingEntities = new ArrayList<>();
        for(SkillTagEntity skillTagEntity : skillTagRepository.getSkillTagEntitiesByIdList(techTechPostReq.getSkillTagIdList())){
            TechPostSkillTagMappingEntity techPostSkillTagMappingEntity = new TechPostSkillTagMappingEntity(
                    techPostEntity, skillTagEntity);
            techPostSkillTagMappingEntities.add(techPostSkillTagMappingEntity);
        }
        return techPostSkillTagMappingEntities;
    }

    public ResponseEntity<List<TechImgRes>> createImg(List<MultipartFile> multipartFileList) throws IOException{
        List<TechImgRes> techImgResList = new ArrayList<>();
        for(MultipartFile multipartFile : multipartFileList)
        {
            UUID uuid = UUID.randomUUID();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(multipartFile.getSize());
            amazonS3Client.putObject(bucket, pathImg + uuid, multipartFile.getInputStream(), metadata);
            FileEntity fileEntity = new FileEntity(uuid.toString());
            fileRepository.save(fileEntity);
            techImgResList.add(new TechImgRes(fileEntity.getId(), path + pathImg + uuid));
        }
        return new ResponseEntity<>(techImgResList, HttpStatus.CREATED);

    }

    public ResponseEntity<HttpStatus> deletePost(Integer techPostId) {
        techPostRepository.delete(techPostRepository.findByTechPostId(techPostId));
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }
}