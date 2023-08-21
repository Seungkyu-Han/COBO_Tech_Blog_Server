package cobo.blog.domain.Tech.Data.Dto.Res;

import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Data.Entity.TechPostSkillTagMappingEntity;
import cobo.blog.global.Util.DateConversion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class TechTechPostDetailRes {

    private UserInTechTechPostRes user;

    @ApiModelProperty(
            value = "TechPost 제목",
            example = "성능테스트 결과와 개선할 점"
    )
    private String title;
    @ApiModelProperty(
            value = "TechPost 작성일자",
            example = "20230723"
    )
    private String createdAt;
    @ApiModelProperty(
            value = "TechPost 사용된 SkillTag들",
            example = "SpringBoot"
    )
    private List<SkillTagInTechTechPostRes> skillTags;

    @ApiModelProperty(
            value = "해당 TechPost 내용",
            example = "문자열로 쭉 ~~"
    )
    private String detail;

    @ApiModelProperty(
            value = "파일의 Id와 url를 key value로"
    )
    private HashMap<Integer, String> fileIdUrlMap;

    @ApiModelProperty(
            value = "파일의 url과 id를 key value로 이거랑 <id, url> 중에서 쓰기 편한거만 남기고 삭제할 예정"
    )
    private HashMap<String, Integer> fileUrlIdMap;

    public TechTechPostDetailRes(TechPostEntity techPostEntity, String detail, HashMap<Integer, String> fileIdUrlMap, HashMap<String, Integer> fileUrlIdMap){
        this.user = new UserInTechTechPostRes(techPostEntity.getUser());
        this.title = techPostEntity.getTitle();
        this.createdAt = DateConversion.DateToString(techPostEntity.getCreatedAt());
        this.skillTags = new ArrayList<>();
        for(TechPostSkillTagMappingEntity techPostSkillTagMappingEntity : techPostEntity.getTechPostSkillTagMappings())
            this.skillTags.add(new SkillTagInTechTechPostRes(techPostSkillTagMappingEntity.getSkillTag()));
        this.detail = detail;
        this.fileIdUrlMap = fileIdUrlMap;
        this.fileUrlIdMap = fileUrlIdMap;
    }
}
