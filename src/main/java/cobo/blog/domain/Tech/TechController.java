package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.Req.TechTechPostReq;
import cobo.blog.domain.Tech.Data.Dto.Req.TechTechUpdateReq;
import cobo.blog.domain.Tech.Data.Dto.Res.TechImgRes;
import cobo.blog.domain.Tech.Data.Dto.Res.TechSkillTagRes;
import cobo.blog.domain.Tech.Data.Dto.Res.TechTechPostDetailRes;
import cobo.blog.domain.Tech.Data.Dto.Res.TechTechPostRes;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            notes = "skillTagId에 값이 0이면 모두 조회, 있으면 그 skillTagId로 조회",
            response = TechTechPostRes.class
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호", example = "1", required = true),
            @ApiImplicitParam(name = "size", value = "페이지의 사이즈", example = "10", required = true),
            @ApiImplicitParam(name = "skillTagId", value = "스킬태그의 Id(0이면 모두 조회)", example = "1", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<List<TechTechPostRes>> getPosts(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "skillTagId") Integer skillTagId){
        return techService.getPosts(page, size, skillTagId);
    }

    @GetMapping("/count")
    @ApiOperation(
            value = "tech의 개수를 가져오는 API",
            notes = "0이면 모든 techPost 개수 조회",
            response = Integer.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공")
    })
    public ResponseEntity<Long> getProjectCount(
            @RequestParam(name = "skillTagId") Integer skillTagId
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
    
    @GetMapping("/post")
    @ApiOperation(
            value = "post의 내용을 가져오는 API",
            notes = "id로 검색합니다.",
            response = TechTechPostDetailRes.class
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "techPostId", value = "검색할 TechPost의 Id", example = "1", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "응답 성공"),
            @ApiResponse(code = 400, message = "해당하는 Id의 TechPost가 없습니다.")
    })
    public ResponseEntity<TechTechPostDetailRes> readPost(
            @RequestParam("techPostId") Integer techPostId
    )throws IOException{
        return techService.readPost(techPostId);
    }

    @PostMapping("/post")
    @ApiOperation(
            value = "techPost를 작성하는 API",
            notes = "json 작성이 잘못되면 실행 자체가 안됩니다.",
            response = HttpStatus.class
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "TechPost 업로드 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 403, message = "인증 실패")
    })
    public ResponseEntity<HttpStatus> createPost(
            @RequestBody TechTechPostReq techTechPostReq){
        return techService.createPost(techTechPostReq);
    }

    @PatchMapping("/post")
    @ApiOperation(
            value = "techPost를 수정하는 API",
            notes = "이것도 일단 대충 만들어봄",
            response = HttpStatus.class
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "TechPost 업로드 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 403, message = "인증 실패")
    })
    public ResponseEntity<HttpStatus> updatePost(
            @RequestBody TechTechUpdateReq techTechUpdateReq
    ){
        return techService.updatePost(techTechUpdateReq);
    }

    @DeleteMapping("/post")
    @ApiOperation(
            value = "techPost를 삭제하는 API",
            notes = "이것도 일단 대충 만들어봄",
            response = HttpStatus.class
    )
    @ApiImplicitParams(
            @ApiImplicitParam(name = "techPostId", value = "삭제할 techPost의 Id", example = "1", required = true)
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 400, message = "해당 Id의 TechPost가 존재하지 않음")
    })
    public ResponseEntity<HttpStatus> deletePost(
            @RequestParam("techPostId") Integer techPostId
    ){
        return techService.deletePost(techPostId);
    }

    @PostMapping(value = "/img",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "techPost에 이미지를 올리는 API",
            notes = "사용하기 위해 인증 필요"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "응답 성공"),
            @ApiResponse(code = 403, message = "인증 실패")
    })
    public ResponseEntity<List<TechImgRes>> createImg(
            @RequestPart(value = "multipartFile") MultipartFile multipartFile
    )throws IOException{
        return techService.createImg(multipartFile);
    }
}