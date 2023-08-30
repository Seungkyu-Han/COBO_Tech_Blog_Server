package cobo.blog.domain.All;

import cobo.blog.domain.All.Data.Dto.Req.AllDeleteLoginReq;
import cobo.blog.domain.All.Data.Dto.Req.AllPatchLoginReq;
import cobo.blog.domain.All.Data.Dto.Res.AllGetUserRes;
import cobo.blog.domain.All.Data.Dto.Res.AllHitRes;
import cobo.blog.domain.All.Data.Dto.Res.AllLoginRes;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isCount", value = "조회수를 증가해야 하는지 bool", example = "true", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<AllHitRes> hit(
            @RequestParam Boolean isCount
    ){
        return allService.getHit(isCount);
    }

    @ResponseBody
    @GetMapping("/login")
    @ApiOperation(
            value = "로그인 후 AccessToken과 RefreshToken을 받아온다.",
            notes = "추후 수정 가능",
            response = AllLoginRes.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공"),
            @ApiResponse(code = 403, message = "유요하지 않은 code")
    })
    public ResponseEntity<AllLoginRes> login(@RequestParam String code) throws IOException{
        return allService.login(code);
    }

    @ResponseBody
    @PatchMapping("/login")
    @ApiOperation(
            value = "RefreshToken을 이용하여 AccessToken을 재발급한다.",
            response = AllLoginRes.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답성공"),
            @ApiResponse(code = 403, message = "해당 RefreshToken이 서버에는 존재하지 않음")
    })
    public ResponseEntity<AllLoginRes> login(@RequestBody AllPatchLoginReq allPatchLoginReq){
        return allService.login(allPatchLoginReq);
    }

    @DeleteMapping("login")
    @ApiOperation(
            value = "로그아웃 시에 서버에 있는 토큰들도 모두 삭제",
            notes = "Body로 RefreshToken만 보내주면 됩니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답성공")
    })
    public ResponseEntity<HttpStatus> logout(@RequestBody AllDeleteLoginReq allDeleteLoginReq){
        return allService.logout(allDeleteLoginReq);
    }

    @GetMapping("/check")
    @ApiOperation(
            value = "현재 로그인이 되어있는지 체크하는 API",
            notes = "Authorization 헤더에 AccessToken을 전송",
            response = AllGetUserRes.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "응답성공"),
            @ApiResponse(code = 403, message = "AccessToken이 아님"),
            @ApiResponse(code = 500, message = "해당 토큰이 유효하지 않음")
    })
    public ResponseEntity<AllGetUserRes> check(@ApiIgnore Authentication authentication){
        return allService.check(authentication);
    }
}