package cobo.blog.domain.Home;

import cobo.blog.domain.Home.Data.Dto.ProjectRes;
import cobo.blog.domain.Home.Data.Dto.TechPostRes;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@AllArgsConstructor
public class HomeController {

    private final HomeServiceImpl homeService;

    @GetMapping("/project")
    @ApiOperation(
            value = "Home 화면에서 Project 정보들을 가져오는 API",
            notes = "몇개 씩 가져올 지 협의 필요하다고 생각"
    )
    public ResponseEntity<List<ProjectRes>> getProjects(){
        return homeService.getProjects();
    }

    @GetMapping("/tech-post")
    @ApiOperation(
            value = "Home 화면에서 Tech post 정보들을 가져오는 API",
            notes = "몇개 씩 가져올 지 협의 필요하다고 생각"
    )
    public ResponseEntity<List<TechPostRes>> getTechPosts(){return homeService.getTechPosts();}
}
