package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.TechPostRes;
import cobo.blog.global.Data.Entity.SkillTagEntity;
import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Data.Entity.TechPostSkillTagMappingEntity;
import cobo.blog.global.Repository.SkillTagRepository;
import cobo.blog.global.Repository.TechPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TechServiceImpl {

    private final TechPostRepository techPostRepository;

    public ResponseEntity<List<TechPostRes>> getPosts() {
        List<TechPostRes> techPostRes = new ArrayList<>();
        for(TechPostEntity techPostEntity : techPostRepository.findAll())
            techPostRes.add(new TechPostRes(techPostEntity));
        return new ResponseEntity<>(techPostRes, HttpStatus.OK);
    }
}
