package cobo.blog.domain.Home;

import cobo.blog.domain.Home.Data.Dto.ProjectRes;
import cobo.blog.domain.Home.Data.Dto.TechPostRes;
import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Data.Entity.ProjectSkillTagMappingEntity;
import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Repository.ProjectRepository;
import cobo.blog.global.Repository.TechPostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class HomeServiceImpl {

    private final ProjectRepository projectRepository;
    private final TechPostRepository techPostRepository;

    public ResponseEntity<List<ProjectRes>> getProjects() {
        List<ProjectRes> projectRes = new ArrayList<>();
        for(ProjectEntity project : projectRepository.findAll())
            projectRes.add(new ProjectRes(project));
        return new ResponseEntity<>(projectRes, HttpStatus.OK);
    }

    public ResponseEntity<List<TechPostRes>> getTechPosts() {
        List<TechPostRes> techPostRes = new ArrayList<>();
        for(TechPostEntity techPostEntity : techPostRepository.findAll())
            techPostRes.add(new TechPostRes(techPostEntity));
        return new ResponseEntity<>(techPostRes, HttpStatus.OK);
    }
}
