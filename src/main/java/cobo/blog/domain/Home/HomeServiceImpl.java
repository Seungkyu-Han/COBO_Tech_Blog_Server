package cobo.blog.domain.Home;

import cobo.blog.domain.Home.Data.Dto.HomeProjectRes;
import cobo.blog.domain.Home.Data.Dto.HomeTechPostRes;
import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Repository.ProjectRepository;
import cobo.blog.global.Repository.TechPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    /**
     * 프로젝트 6개를 카드로 반환하는 함수
     * @return 6개의 프로젝트 카드 DTO
     * @Author Seungkyu-Han
     */
    public ResponseEntity<List<HomeProjectRes>> getProjects() {
        List<HomeProjectRes> homeProjectRes = new ArrayList<>();
        for(ProjectEntity project : projectRepository.findTop6ByOrderByIdDesc())
            homeProjectRes.add(new HomeProjectRes(project));
        return new ResponseEntity<>(homeProjectRes, HttpStatus.OK);
    }

    /**
     * TechPost 8개의 TechPost를 간단하게 DTO로 반환하는 함수
     * @return 8개의 TechPost DTO
     * @Author Seungkyu-Han
     */
    public ResponseEntity<List<HomeTechPostRes>> getTechPosts() {
        List<HomeTechPostRes> homeTechPostRes = new ArrayList<>();
        for(TechPostEntity techPostEntity : techPostRepository.findTop8ByOrderByIdDesc())
            homeTechPostRes.add(new HomeTechPostRes(techPostEntity));
        return new ResponseEntity<>(homeTechPostRes, HttpStatus.OK);
    }
}
