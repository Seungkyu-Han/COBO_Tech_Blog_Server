package cobo.blog.domain.All;

import cobo.blog.domain.About.Data.Dto.AboutMemberRes;
import cobo.blog.domain.All.Data.Dto.AllHitRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/all")
@AllArgsConstructor
@Api(tags = {"05. 모든 화면에 사용하는 API"})
public class AllController {

    private final AllServiceImpl allService;

    @GetMapping("/hit")
    @ApiOperation(
            value = "블로그의 조회 수를 가져온다,",
            notes = "today 오늘 조회수, total 전체 조회수(오늘 조회도 합친)",
            response = AllHitRes.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<AllHitRes> hit(
            @ApiIgnore @CookieValue(value = "hitCookie", defaultValue = "0") Integer hitCookie,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse){
        return allService.getHit(hitCookie, httpServletRequest, httpServletResponse);
    }
}
