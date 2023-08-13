package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.Req.TechTechPostReq;
import cobo.blog.domain.Tech.Data.Dto.Res.TechSkillTagRes;
import cobo.blog.domain.Tech.Data.Dto.Res.TechTechPostRes;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            notes = "페이지 1부터 시작합니다, 주의 부탁드립니다.\n" +
                    "skillTagId에 값이 없으면 모두 조회, 있으면 그 skillTagId로 조회",
            response = TechTechPostRes.class
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1", required = true),
            @ApiImplicitParam(name = "size", value = "페이지의 사이즈", example = "10", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<List<TechTechPostRes>> getPosts(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "skillTagId", required = false) Integer skillTagId){
        return techService.getPosts(page, size, skillTagId);
    }

    @GetMapping("/count")
    @ApiOperation(
            value = "tech의 개수를 가져오는 API",
            notes = "이건 그냥 숫자로 바로 때리겠습니다.",
            response = Integer.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<Long> getProjectCount(
            @RequestParam(name = "skillTagId", required = false) Integer skillTagId
    ){
        return techService.getTechCount(skillTagId);
    }

    @GetMapping("/skillTags")
    @ApiOperation(
            value = "skillTag들을 가져오는 API",
            notes = "그냥 문자열 리스트로 뿌립니다.",
            response = TechSkillTagRes.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<List<TechSkillTagRes>> getSkillTags(){return techService.getSkillTags();}

    @GetMapping("/posts-skilltag")
    @ApiOperation(
            value = "SkillTag id에 맞는 techPost를 불러오는 API",
            notes = "삭제 예정",
            response = TechTechPostRes.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<List<TechTechPostRes>> getPostsBySkillTag(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("skillTagId") Integer skillTagId){
        return techService.getPostsBySkillTag(page, size, skillTagId);
    }

    @GetMapping("/post")
    @ApiOperation(
            value = "post의 내용을 가져오는 API",
            notes = "id로 검색합니다.",
            response = TechTechPostRes.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<TechTechPostRes> getPost(
            @RequestParam("techPostId") Integer techPostId
    ){
        return techService.getPost(techPostId);
    }

    @PostMapping("/post")
    @ApiOperation(
            value = "techPost를 작성하는 API",
            notes = "일단 대충 만들어봄",
            response = HttpStatus.class
    )
    public ResponseEntity<HttpStatus> createPost(
            @ModelAttribute(value = "techTechPostReq") TechTechPostReq techTechPostReq,
            @RequestPart(value = "multipartFile") MultipartFile multipartFile){
        return techService.createPost(techTechPostReq, multipartFile);
    }

}