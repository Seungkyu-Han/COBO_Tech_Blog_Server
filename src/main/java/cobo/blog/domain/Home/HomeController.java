package cobo.blog.domain.Home;

import cobo.blog.domain.Home.Data.Dto.HomeProjectRes;
import cobo.blog.domain.Home.Data.Dto.HomeTechPostRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@AllArgsConstructor
@Api(tags = {"01. Home 화면에 사용하는 API"})
public class HomeController {

    private final HomeServiceImpl homeService;

    @GetMapping("/project")
    @ApiOperation(
            value = "Home 화면에서 Project 정보들을 가져오는 API",
            notes = "현재 6개의 Project 넘김",
            response = HomeProjectRes.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<List<HomeProjectRes>> getProjects(){
        return homeService.getProjects();
    }

    @GetMapping("/tech-post")
    @ApiOperation(
            value = "Home 화면에서 Tech post 정보들을 가져오는 API",
            notes = "현재 8개의 Tech-Post info 넘김",
            response = HomeTechPostRes.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<List<HomeTechPostRes>> getTechPosts(){return homeService.getTechPosts();}
}
