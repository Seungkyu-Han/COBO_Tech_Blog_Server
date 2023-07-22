package cobo.blog.domain.Project;

import cobo.blog.domain.Project.Data.Dto.ProjectCardRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/project")
@Api(tags = {"04. Project 화면에 사용하는 API"})
public class ProjectController {

    private final ProjectServiceImpl projectService;

    @GetMapping("/project-cards")
    @ApiOperation(
            value = "Project card 내용을 가져오는 API",
            notes = "아몰랑"
    )
    public ResponseEntity<List<ProjectCardRes>> getProjectCards(){
        return projectService.getProjectCards();
    }
}
