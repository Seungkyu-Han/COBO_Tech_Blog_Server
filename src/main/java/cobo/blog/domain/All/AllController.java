package cobo.blog.domain.All;

import cobo.blog.domain.All.Data.Dto.Res.AllHitRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/all")
@AllArgsConstructor
@Api(tags = {"00. 모든 화면에 사용하는 API"})
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
            HttpServletResponse httpServletResponse
    ){
        return allService.getHit(hitCookie, httpServletResponse);
    }

    @ResponseBody
    @GetMapping("/login")
    public ResponseEntity<Integer> login(@RequestParam String code, HttpServletResponse httpServletResponse) throws IOException{
        return allService.login(code, httpServletResponse);
    }
    @DeleteMapping("/login")
    public ResponseEntity<HttpStatus> logout(
            @ApiIgnore Authentication authentication,
            HttpServletResponse httpServletResponse
            ){
        return allService.logout(authentication, httpServletResponse);
    }

    @GetMapping("/check")
    public ResponseEntity<String> check(){
        return allService.check();
    }

}