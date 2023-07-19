package cobo.blog.domain.About;

import cobo.blog.domain.About.Data.Dto.MemberRes;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/about")
@AllArgsConstructor
public class AboutController {

    private final AboutServiceImpl aboutService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberRes>> getMembers(){
        return aboutService.getMembers();
    }
}
