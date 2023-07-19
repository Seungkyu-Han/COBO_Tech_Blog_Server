package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.TechPostRes;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tech")
@AllArgsConstructor
public class TechController {

    private final TechServiceImpl techService;

    @GetMapping("/posts")
    public ResponseEntity<List<TechPostRes>> getPosts(){
        return techService.getPosts();
    }
}
