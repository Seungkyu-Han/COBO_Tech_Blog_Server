package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.Req.TechTechPostReq;
import cobo.blog.domain.Tech.Data.Dto.Req.TechTechUpdateReq;
import cobo.blog.domain.Tech.Data.Dto.Res.TechImgRes;
import cobo.blog.domain.Tech.Data.Dto.Res.TechSkillTagRes;
import cobo.blog.domain.Tech.Data.Dto.Res.TechTechPostDetailRes;
import cobo.blog.domain.Tech.Data.Dto.Res.TechTechPostRes;
import cobo.blog.global.Data.Entity.*;
import cobo.blog.global.Repository.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
    private static final String txtExtension = ".txt";

    /**
     * TechPost 페이징해서 리스트로 반환하는 메서드
     * @param page 가져오고 싶은 페이지
     * @param size 한 페이지에 들어가는 TechPost 개수
     * @param skillTagId 필터링하고 싶은 SkillTag (0이면 전부 검색)
     * @return ResponseEntity<List<TechPostRes>> 페이징 한 TechPostRes 리스트로 반환
     * @Author Seungkyu-Han
     */
    public ResponseEntity<List<TechTechPostRes>> getPosts(Integer page, Integer size, Integer skillTagId) {
        List<TechTechPostRes> techTechPostRes = new ArrayList<>();
        PageRequest pageRequest = pageRequestGenerator(page, size, Sort.Direction.DESC, "id");
        for(TechPostEntity techPostEntity : (skillTagId == 0) ?
                techPostRepository.findAll(pageRequest) : techPostRepository.getTechPostEntitiesBySkillTagId(skillTagId, pageRequest))
            techTechPostRes.add(new TechTechPostRes(techPostEntity, path));
        return new ResponseEntity<>(techTechPostRes, HttpStatus.OK);
    }

    /**
     * 해당하는 TechPost 글의 개수를 반환하는 메서드
     * @param skillTagId 필터링하고 싶은 SkillTag
     * @return ResponseEntity<Long> 해당하는 개수를 반환
     * @Author Seungkyu-Han
     */
    public ResponseEntity<Long> getTechCount(Integer skillTagId) {
        return (skillTagId == 0) ?
                new ResponseEntity<>(techPostRepository.count(), HttpStatus.OK):
                new ResponseEntity<>(techPostRepository.countBySkillTagId(skillTagId), HttpStatus.OK);
    }


    /**
     * 모든 SkillTag의 id와 이름을 전달
     * @return 이름과 id가 들어있는 dto list
     */
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

    public ResponseEntity<TechTechPostDetailRes> readPost(Integer techPostId) throws IOException {
        Optional<TechPostEntity> techPostEntityOptional = techPostRepository.findById(techPostId);
        if(techPostEntityOptional.isEmpty())
            throw new NullPointerException();
        redisTemplate.opsForValue().increment(techPostRedisName + techPostId);
        HashMap<Integer, String> fileIdUrlMap = new HashMap<>();
        HashMap<String, Integer> fileUrlIdMap = new HashMap<>();
        for(FileEntity fileEntity : fileRepository.findAllByTechPost(techPostEntityOptional.get()))
        {
            fileIdUrlMap.put(fileEntity.getId(), path + fileEntity.getFileName());
            fileUrlIdMap.put(path + fileEntity.getFileName(), fileEntity.getId());
        }
        return new ResponseEntity<>(
                new TechTechPostDetailRes(techPostEntityOptional.get(),
                        getStringFromS3(techPostEntityOptional.get().getFileName()), fileIdUrlMap, fileUrlIdMap), HttpStatus.OK);
    }

    /**
     * 작성한 글을 업로드하는 메서드(SkillTag, file 연관 로직까지 수행)
     * @param techTechPostReq TechPost DTO
     * @return ResponseEntity
     * @Author Seungkyu-Han
     */
    @Transactional
    public ResponseEntity<HttpStatus> createPost(TechTechPostReq techTechPostReq){

        String uuidName = uploadStringToS3(techTechPostReq.getDetail());

        Optional<UserEntity> userEntityOptional = userRepository.findById(techTechPostReq.getUserId());

        if(userEntityOptional.isEmpty()) throw new NullPointerException();

        TechPostEntity techPostEntity = new TechPostEntity
                (
                        techTechPostReq.getTitle(),
                        techTechPostReq.getContent(),
                        uuidName,
                        userEntityOptional.get()
                );

        skillTagMapping(skillTagRepository.getSkillTagEntitiesByIdList(techTechPostReq.getSkillTagIdList()), techPostEntity);
        techPostRepository.save(techPostEntity);

        redisTemplate.opsForValue().set(techPostRedisName + techPostEntity.getId(), "0");

        for(FileEntity fileEntity : fileRepository.findAllById(techTechPostReq.getFileIdList())){
            fileEntity.setTechPost(techPostEntity);
            fileRepository.save(fileEntity);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 작성한 글을 수정하는 메서드(수정 예정)
     * @param techTechUpdateReq update 요청하는 dto
     * @return ResponseEntity<HttpStatus>
     * @Author  Seungkyu-Han
     */
    @Transactional
    public ResponseEntity<HttpStatus> updatePost(TechTechUpdateReq techTechUpdateReq){

        Optional<TechPostEntity> techPostEntityOptional = techPostRepository.findById(techTechUpdateReq.getTechPostId());
        if(techPostEntityOptional.isEmpty())
            throw new NullPointerException();

        techPostSkillTagMappingRepository.deleteAllByTechPost(techPostEntityOptional.get());
        amazonS3Client.deleteObject(bucket, pathTxt + techPostEntityOptional.get().getFileName());
        deleteImg(techTechUpdateReq.getDeleteFileIdList());

        String uuidName = uploadStringToS3(techTechUpdateReq.getDetail());

        techPostEntityOptional.get().UpdateByTechTechPostReqAndUrl(techTechUpdateReq,uuidName);

        skillTagMapping(skillTagRepository.getSkillTagEntitiesByIdList(techTechUpdateReq.getSkillTagIdList()), techPostEntityOptional.get());

        techPostRepository.save(techPostEntityOptional.get());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * techPost 글을 삭제하는 메서드 (S3에서 이미지, txt를 삭제하고 DB에 있는 내용을 삭제함, SkillTag, File 매핑도 삭제)
     * @param techPostId 삭제하고 싶은 techPost의 ID
     * @return ResponseEntity<HttpStatus>
     * @Author Seungkyu-Han
     */
    @Transactional
    public ResponseEntity<HttpStatus> deletePost(Integer techPostId) {
        Optional<TechPostEntity> techPostEntityOptional = techPostRepository.findById(techPostId);
        if(techPostEntityOptional.isEmpty())
            throw new NullPointerException();
        for(FileEntity fileEntity : fileRepository.findAllByTechPost(techPostEntityOptional.get()))
            amazonS3Client.deleteObject(bucket, fileEntity.getFileName());
        fileRepository.deleteAllByTechPost(techPostEntityOptional.get());
        amazonS3Client.deleteObject(bucket, pathTxt + techPostEntityOptional.get().getFileName());
        redisTemplate.delete("techPost" + techPostId);
        techPostRepository.delete(techPostEntityOptional.get());
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    /**
     * SkillTag와 techPost를 mapping 시켜주는 메서드
     * @param skillTagEntities 매핑할 SkillTag 리스트
     * @param techPostEntity 매핑할 techPost
     * @Author Seungkyu-Han
     */
    private void skillTagMapping(List<SkillTagEntity> skillTagEntities, TechPostEntity techPostEntity){
        List<TechPostSkillTagMappingEntity> techPostSkillTagMappingEntities = new ArrayList<>();
        for(SkillTagEntity skillTagEntity : skillTagEntities){
            TechPostSkillTagMappingEntity techPostSkillTagMappingEntity = new TechPostSkillTagMappingEntity(
                    techPostEntity, skillTagEntity);
            techPostSkillTagMappingEntities.add(techPostSkillTagMappingEntity);
        }
        techPostSkillTagMappingRepository.saveAll(techPostSkillTagMappingEntities);
    }

    /**
     * 이미지들을 S3에 업로드하는 메서드
     * @param multipartFile 업로드 할 파일
     * @return 해당 이미지의 id와 url 경로를 dto로 담아서 리스트로 전송
     * @throws IOException 바이트로 나누어서 올리는 과정에서 생기는 에러
     * @Author Seungkyu-Han
     */
    public ResponseEntity<List<TechImgRes>> createImg(MultipartFile multipartFile) throws IOException{
        List<TechImgRes> techImgResList = new ArrayList<>();

        String uuidName = uploadToS3(multipartFile);

        FileEntity fileEntity = new FileEntity(uuidName);
        fileRepository.save(fileEntity);

        techImgResList.add(new TechImgRes(fileEntity.getId(), uuidName));
        return new ResponseEntity<>(techImgResList, HttpStatus.CREATED);
    }

    /**
     * S3에 업로드 한 이미지를 삭제하는 메서드
     * @param fileIdList 삭제할 이미지들의 Id List
     * @Author Seungkyu-Han
     */
    private void deleteImg(List<Integer> fileIdList) {
        List<FileEntity> fileEntities = fileRepository.findAllById(fileIdList);
        for(FileEntity fileEntity : fileEntities)
            amazonS3Client.deleteObject(bucket,fileEntity.getFileName());
        fileRepository.deleteAllById(fileIdList);
    }

    /**
     * 파일을 업로드 하는 메서드
     * @param multipartFile 업로드 할 파일
     * @return uuid + extension
     * @throws IOException 파일을 업로드하다가 발생하는 에러
     * @Author Seungkyu-Han
     */
    private String uploadToS3(MultipartFile multipartFile) throws IOException{
        String uuidName = pathImg + UUID.randomUUID() + "." + getExtension(multipartFile);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        amazonS3Client.putObject(bucket,  uuidName, multipartFile.getInputStream(), metadata);
        return uuidName;
    }

    /**
     * 문자열을 S3에 업로드하는 메서드
     * @param data 저장하고 싶은 문자열
     * @return 저장하고 난 파일의 이름
     * @Author Seungkyu-Han
     */
    private String uploadStringToS3(String data){
        String uuidName = pathTxt + UUID.randomUUID() + txtExtension;
        byte[] byteData = data.getBytes(StandardCharsets.UTF_8);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(byteData.length);
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucket, uuidName, new ByteArrayInputStream(byteData), metadata);

        amazonS3Client.putObject(putObjectRequest);
        return uuidName;
    }

    /**
     * 파일의 확장자를 가져오는 메서드
     * @param multipartFile Extension을 가져올 파일
     * @return 해당 파일의 Extension
     * @Author Seungkyu-Han
     */
    private String getExtension(MultipartFile multipartFile){
        return StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
    }

    /**
     * 해당 파일의 내용을 String으로 읽어 옴
     * @param key 읽어올 파일의 이름
     * @return 해당 파일을 문자열로 읽어 옴
     * @throws IOException BufferRead 과정에서 에러
     * @Author Seungkyu-Han
     */
    private String getStringFromS3(String key) throws IOException{
        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucket, key));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
        return bufferedReader.readLine();
    }
}