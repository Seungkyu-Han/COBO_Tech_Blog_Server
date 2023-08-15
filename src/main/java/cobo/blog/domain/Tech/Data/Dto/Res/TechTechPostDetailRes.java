package cobo.blog.domain.Tech.Data.Dto.Res;

import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Data.Entity.TechPostSkillTagMappingEntity;
import cobo.blog.global.Util.DateConversion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
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
            value = "TechPost 사용된 SkillTag 이름들",
            example = "SpringBoot"
    )
    private List<String> skillTag;

    @ApiModelProperty(
            value = "해당 TechPost 내용",
            example = "문자열로 쭉 ~~"
    )
    private String detail;

    public TechTechPostDetailRes(TechPostEntity techPostEntity, String detail){
        this.user = new UserInTechTechPostRes(techPostEntity.getUser());
        this.title = techPostEntity.getTitle();
        this.createdAt = DateConversion.DateToString(techPostEntity.getCreatedAt());
        this.skillTag = new ArrayList<>();
        for(TechPostSkillTagMappingEntity techPostSkillTagMappingEntity : techPostEntity.getTechPostSkillTagMappings())
            this.skillTag.add(techPostSkillTagMappingEntity.getSkillTag().getName());
        this.detail = detail;
    }
}
