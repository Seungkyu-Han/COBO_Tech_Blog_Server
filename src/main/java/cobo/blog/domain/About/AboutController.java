package cobo.blog.domain.About;

import cobo.blog.domain.About.Data.Dto.MemberRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/about")
@AllArgsConstructor
@Api(tags = {"02. About 화면에 사용하는 API"})
public class AboutController {

    private final AboutServiceImpl aboutService;

    @GetMapping("/members")
    @ApiOperation(
            value = "현재 멤버의 정보를 가져오는 API"
    )
    public ResponseEntity<List<MemberRes>> getMembers(){
        return aboutService.getMembers();
    }
}
