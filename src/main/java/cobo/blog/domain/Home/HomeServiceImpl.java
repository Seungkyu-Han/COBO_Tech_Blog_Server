package cobo.blog.domain.Home;

import cobo.blog.domain.Home.Data.Dto.HomeProjectRes;
import cobo.blog.domain.Home.Data.Dto.HomeTechPostRes;
import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Repository.ProjectRepository;
import cobo.blog.global.Repository.TechPostRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Component
public class HomeServiceImpl {

    private final ProjectRepository projectRepository;
    private final TechPostRepository techPostRepository;

    @Value("${cloud.aws.s3.path}")
    private String path;

    public ResponseEntity<List<HomeProjectRes>> getProjects() {
        List<HomeProjectRes> homeProjectRes = new ArrayList<>();
        for(ProjectEntity project : projectRepository.findTop6ByOrderByIdDesc())
            homeProjectRes.add(new HomeProjectRes(project));
        return new ResponseEntity<>(homeProjectRes, HttpStatus.OK);
    }

    public ResponseEntity<List<HomeTechPostRes>> getTechPosts() {
        List<HomeTechPostRes> homeTechPostRes = new ArrayList<>();
        for(TechPostEntity techPostEntity : techPostRepository.findTop8ByOrderByIdDesc())
            homeTechPostRes.add(new HomeTechPostRes(techPostEntity, path));
        return new ResponseEntity<>(homeTechPostRes, HttpStatus.OK);
    }
}
