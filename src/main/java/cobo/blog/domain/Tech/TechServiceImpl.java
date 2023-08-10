package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.TechSkillTagRes;
import cobo.blog.domain.Tech.Data.Dto.TechTechPostRes;
import cobo.blog.global.Data.Entity.SkillTagEntity;
import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Repository.SkillTagRepository;
import cobo.blog.global.Repository.TechPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TechServiceImpl {

    private final TechPostRepository techPostRepository;
    private final SkillTagRepository skillTagRepository;

    public ResponseEntity<List<TechTechPostRes>> getPosts(Integer page, Integer size) {
        List<TechTechPostRes> techTechPostRes = new ArrayList<>();
        for(TechPostEntity techPostEntity : techPostRepository.findAll(getPageRequest(page, size)))
            techTechPostRes.add(new TechTechPostRes(techPostEntity));
        return new ResponseEntity<>(techTechPostRes, HttpStatus.OK);
    }

    public ResponseEntity<Long> getTechCount() {
        return new ResponseEntity<>(techPostRepository.count(), HttpStatus.OK);
    }

    public ResponseEntity<List<TechSkillTagRes>> getSkillTags() {
        List<TechSkillTagRes> techSkillTagRes = new ArrayList<>();
        for(SkillTagEntity skillTagEntity : skillTagRepository.findAll())
            techSkillTagRes.add(new TechSkillTagRes(skillTagEntity));
        return new ResponseEntity<>(techSkillTagRes, HttpStatus.OK);
    }

    public ResponseEntity<List<TechTechPostRes>> getPostsBySkillTag(Integer page, Integer size, Integer skillTag) {
        List<TechTechPostRes> techTechPostRes = new ArrayList<>();
        for(TechPostEntity techPostEntity : techPostRepository.getTechPostEntitiesBySkillTagId(skillTag, getPageRequest(page, size)))
            techTechPostRes.add(new TechTechPostRes(techPostEntity));
        return new ResponseEntity<>(techTechPostRes, HttpStatus.OK);
    }

    private static PageRequest getPageRequest(int page, int size) {
        return PageRequest.of(
                page - 1, size, Sort.by(Sort.Direction.DESC, "id")
        );
    }
}