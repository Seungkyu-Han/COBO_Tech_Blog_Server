package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.TechPostRes;
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
@RequestMapping("/api/tech")
@AllArgsConstructor
@Api(tags = {"03. Tech 화면에 사용하는 API"})
public class TechController {

    private final TechServiceImpl techService;

    @GetMapping("/posts")
    @ApiOperation(
            value = "Tech Post 의 정보를 List 응답",
            notes = "현재 전부 넘기고 있어서 몇페이지로 자를 건지 협의가 필요",
            response = TechPostRes.class,
            produces = "application/json"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<List<TechPostRes>> getPosts(){
        return techService.getPosts();
    }
}
