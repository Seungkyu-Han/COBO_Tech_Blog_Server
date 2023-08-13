package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.Req.TechTechPostReq;
import cobo.blog.domain.Tech.Data.Dto.Res.TechSkillTagRes;
import cobo.blog.domain.Tech.Data.Dto.Res.TechTechPostRes;
import cobo.blog.global.Data.Entity.SkillTagEntity;
import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Repository.SkillTagRepository;
import cobo.blog.global.Repository.TechPostRepository;
import cobo.blog.global.Repository.UserRepository;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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
    private final RedisTemplate<String, String> redisTemplate;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.path}")
    private String path;
    public ResponseEntity<List<TechTechPostRes>> getPosts(Integer page, Integer size, Integer skillTagId) {
        List<TechTechPostRes> techTechPostRes = new ArrayList<>();
        PageRequest pageRequest = pageRequestGenerator(page, size, Sort.Direction.DESC, "id");
        for(TechPostEntity techPostEntity : (skillTagId == null) ?
                techPostRepository.findAll(pageRequest) : techPostRepository.getTechPostEntitiesBySkillTagId(skillTagId, pageRequest))
            techTechPostRes.add(new TechTechPostRes(techPostEntity));
        return new ResponseEntity<>(techTechPostRes, HttpStatus.OK);
    }

    public ResponseEntity<Long> getTechCount(Integer skillTagId) {
        return (skillTagId == null) ?
                new ResponseEntity<>(techPostRepository.count(), HttpStatus.OK):
                new ResponseEntity<>(techPostRepository.countTechPostEntitiesBySkillTag(skillTagId), HttpStatus.OK);
    }

    public ResponseEntity<List<TechSkillTagRes>> getSkillTags() {
        List<TechSkillTagRes> techSkillTagRes = new ArrayList<>();
        for(SkillTagEntity skillTagEntity : skillTagRepository.findAll())
            techSkillTagRes.add(new TechSkillTagRes(skillTagEntity));
        return new ResponseEntity<>(techSkillTagRes, HttpStatus.OK);
    }

    //삭제 예
    public ResponseEntity<List<TechTechPostRes>> getPostsBySkillTag(Integer page, Integer size, Integer skillTagId) {
        List<TechTechPostRes> techTechPostRes = new ArrayList<>();
        for(TechPostEntity techPostEntity : techPostRepository.getTechPostEntitiesBySkillTagId(
                skillTagId, pageRequestGenerator(page, size, Sort.Direction.DESC, "id")))
            techTechPostRes.add(new TechTechPostRes(techPostEntity));
        return new ResponseEntity<>(techTechPostRes, HttpStatus.OK);
    }

    public ResponseEntity<TechTechPostRes> getPost(Integer techPostId) {
        redisTemplate.opsForValue().increment("techPost" + techPostId);
        return new ResponseEntity<>(new TechTechPostRes(techPostRepository.findByTechPostId(techPostId)), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> createPost(TechTechPostReq techTechPostReq, MultipartFile multipartFile) {
        try {
            UUID uuidName = UUID.randomUUID();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(multipartFile.getSize());
            amazonS3Client.putObject(bucket,"md/"+ uuidName, multipartFile.getInputStream(), metadata);

            TechPostEntity techPostEntity = TechPostEntity.builder()
                    .title(techTechPostReq.getTitle())
                    .url(path + uuidName)
                    .user(userRepository.getById(techTechPostReq.getUserId()))
                    .build();

            techPostRepository.save(techPostEntity);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}