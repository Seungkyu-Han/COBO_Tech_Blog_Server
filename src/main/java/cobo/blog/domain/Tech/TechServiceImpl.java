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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    @Value("${cloud.aws.s3.path-txt}")
    private String pathTxt;
    @Value("${cloud.aws.s3.path-img}")
    private String pathImg;
    private final String techPostRedisName = "techPost";
    private final String PostExtension = ".txt";

    public ResponseEntity<List<TechTechPostRes>> getPosts(Integer page, Integer size, Integer skillTagId) {
        List<TechTechPostRes> techTechPostRes = new ArrayList<>();
        PageRequest pageRequest = pageRequestGenerator(page, size, Sort.Direction.DESC, "id");
        for(TechPostEntity techPostEntity : (skillTagId == null) ?
                techPostRepository.findAll(pageRequest) : techPostRepository.getTechPostEntitiesBySkillTagId(skillTagId, pageRequest))
            techTechPostRes.add(new TechTechPostRes(techPostEntity, path + pathTxt));
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

    /**
     * techPost 글을 하나 읽어오는 메서드
     * @param techPostId 읽어 올 techPost 글의 Id
     * @return ResponseEntity
     * @Author Seungkyu-Han
     */

    public ResponseEntity<TechTechPostRes> readPost(Integer techPostId) {
        redisTemplate.opsForValue().increment(techPostRedisName + techPostId);
        return new ResponseEntity<>(new TechTechPostRes(techPostRepository.findByTechPostId(techPostId), path + pathTxt), HttpStatus.OK);
    }

    /**
     * 작성한 글을 업로드하는 메서드(SkillTag, file 연관 로직까지 수행)
     * @param techTechPostReq TechPost DTO
     * @param multipartFile 글이 작성된 파일
     * @return ResponseEntity
     * @throws IOException S3 파일 업로드 에러
     * @Author Seungkyu-Han
     */
    @Transactional
    public ResponseEntity<HttpStatus> createPost(TechTechPostReq techTechPostReq, MultipartFile multipartFile) throws IOException{

        String uuidName = uploadToS3(multipartFile, pathTxt);

        TechPostEntity techPostEntity = new TechPostEntity
                (
                        techTechPostReq.getTitle(),
                        techTechPostReq.getContent(),
                        uuidName,
                        userRepository.getById(techTechPostReq.getUserId())
                );

        List<TechPostSkillTagMappingEntity> techPostSkillTagMappingEntities = skillTagMapping(techTechPostReq, techPostEntity);

        techPostSkillTagMappingRepository.saveAll(techPostSkillTagMappingEntities);
        techPostRepository.save(techPostEntity);

        redisTemplate.opsForValue().set(techPostRedisName + techPostEntity.getId(), "0");

        for(FileEntity fileEntity : fileRepository.findAllById(techPostRepository.getTechPostIdList())){
            fileEntity.setTechPost(techPostEntity);
            fileRepository.save(fileEntity);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 작성한 글을 수정하는 메서드(수정 예정)
     * @param techTechPostReq techPostDto
     * @param multipartFile 글이 수정된 파일
     * @return ResponseEntity<HttpStatus>
     * @throws IOException S3 파일 업로드 에러
     */
    @Transactional
    public ResponseEntity<HttpStatus> updatePost(TechTechPostReq techTechPostReq, MultipartFile multipartFile) throws IOException{

        TechPostEntity techPostEntity = techPostRepository.findByTechPostId(techTechPostReq.getTechPostId());

        techPostSkillTagMappingRepository.deleteAllByTechPost(techPostEntity);

        amazonS3Client.deleteObject(bucket, pathTxt + techPostEntity.getFileName());

        String uuidName = uploadToS3(multipartFile, pathTxt);

        techPostEntity.UpdateByTechTechPostReqAndUrl(techTechPostReq,uuidName);

        List<TechPostSkillTagMappingEntity> techPostSkillTagMappingEntities = skillTagMapping(techTechPostReq, techPostEntity);

        techPostSkillTagMappingRepository.saveAll(techPostSkillTagMappingEntities);
        techPostRepository.save(techPostEntity);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * techPost 글을 삭제하는 메서드 (S3에서 이미지, txt를 삭제하고 DB에 있는 내용을 삭제함)
     * @param techPostId 삭제하고 싶은 techPost의 ID
     * @return ResponseEntity<HttpStatus>
     * @Author Seungkyu-Han
     */
    @Transactional
    public ResponseEntity<HttpStatus> deletePost(Integer techPostId) {
        TechPostEntity techPostEntity = techPostRepository.findByTechPostId(techPostId);
        for(FileEntity fileEntity : fileRepository.findAllByTechPost(techPostEntity))
            amazonS3Client.deleteObject(bucket, pathImg + fileEntity.getFileName());
        fileRepository.deleteAllByTechPost(techPostEntity);
        amazonS3Client.deleteObject(bucket, pathTxt + techPostEntity.getFileName());
        techPostRepository.delete(techPostRepository.findByTechPostId(techPostId));
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
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
            String uuidName = uploadToS3(multipartFile, pathImg);

            FileEntity fileEntity = new FileEntity(uuidName);
            fileRepository.save(fileEntity);

            techImgResList.add(new TechImgRes(fileEntity.getId(), path + pathImg + uuidName));
        }
        return new ResponseEntity<>(techImgResList, HttpStatus.CREATED);

    }

    /**
     * 파일을 업로드 하는 메서드
     * @param multipartFile 업로드 할 파일
     * @param path 업로드 할 파일의 경로
     * @return uuid + extension
     * @throws IOException 파일을 업로드하다가 발생하는 에러
     * @Author Seungkyu-Han
     */
    private String uploadToS3(MultipartFile multipartFile, String path) throws IOException{
        String uuidName = UUID.randomUUID() + getExtension(multipartFile);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        amazonS3Client.putObject(bucket,  path + uuidName, multipartFile.getInputStream(), metadata);
        return uuidName;
    }

    /**
     *
     * @param multipartFile Extension을 가져올 파일
     * @return 해당 파일의 Extension
     * @Author Seungkyu-Han
     */
    private String getExtension(MultipartFile multipartFile){
        return StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
    }
}