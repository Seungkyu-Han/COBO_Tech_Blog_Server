package cobo.blog.domain.Home;

import cobo.blog.domain.Home.Data.Dto.ProjectRes;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ProjectRes>> getProjects(){
        return homeService.getProjects();
    }
}
