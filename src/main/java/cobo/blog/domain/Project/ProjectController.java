package cobo.blog.domain.Project;

import cobo.blog.domain.Project.Data.Dto.ProjectProjectCardRes;
import cobo.blog.domain.Tech.Data.Dto.TechTechPostRes;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            notes = "이것도 전체 다 가져오는 데, 몇개 가져올 지 이야기 필요",
            response = TechTechPostRes.class
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1", required = true),
            @ApiImplicitParam(name = "size", value = "페이지의 사이즈", example = "6", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<List<ProjectProjectCardRes>> getProjectCards(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size){
        return projectService.getProjectCards(page, size);
    }

    @GetMapping("/count")
    @ApiOperation(
            value = "Project의 개수를 가져오는 API",
            notes = "이건 그냥 숫자로 바로 때리겠습니다.",
            response = Integer.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<Long> getProjectCount(){
        return projectService.getProjectCount();
    }
}
