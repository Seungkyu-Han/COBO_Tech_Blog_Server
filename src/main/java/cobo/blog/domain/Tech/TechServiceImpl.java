package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.TechSkillTagRes;
import cobo.blog.domain.Tech.Data.Dto.TechTechPostRes;
import cobo.blog.global.Data.Entity.SkillTagEntity;
import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Repository.SkillTagRepository;
import cobo.blog.global.Repository.TechPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cobo.blog.global.Util.PageRequestUtil.pageRequestGenerator;

@Service
@AllArgsConstructor
public class TechServiceImpl {

    private final TechPostRepository techPostRepository;
    private final SkillTagRepository skillTagRepository;

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

    //삭제 예정
    public ResponseEntity<List<TechTechPostRes>> getPostsBySkillTag(Integer page, Integer size, Integer skillTagId) {
        List<TechTechPostRes> techTechPostRes = new ArrayList<>();
        for(TechPostEntity techPostEntity : techPostRepository.getTechPostEntitiesBySkillTagId(
                skillTagId, pageRequestGenerator(page, size, Sort.Direction.DESC, "id")))
            techTechPostRes.add(new TechTechPostRes(techPostEntity));
        return new ResponseEntity<>(techTechPostRes, HttpStatus.OK);
    }

    public ResponseEntity<TechTechPostRes> getPost(Integer techPostId) {
        return new ResponseEntity<>(new TechTechPostRes(techPostRepository.findByTechPostId(techPostId)), HttpStatus.OK);
    }
}