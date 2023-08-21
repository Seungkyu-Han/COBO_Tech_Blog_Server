package cobo.blog.domain.Tech.Data.Dto.Res;

import cobo.blog.global.Data.Entity.SkillTagEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillTagInTechTechPostRes {

    @ApiModelProperty(
            value = "skillTag 이름",
            example = "springBoot"
    )
    private String name;

    @ApiModelProperty(
            value = "해당 skillTag 색상코드",
            example = "#aaaaaa"
    )
    private String color;

    @ApiModelProperty(
            value = "해당 skillTag 글자 색",
            example = "true"
    )
    private Boolean isBlack;

    public SkillTagInTechTechPostRes(SkillTagEntity skillTagEntity){
        this.name = skillTagEntity.getName();
        this.color = skillTagEntity.getColor();
        this.isBlack = skillTagEntity.getIsBlack();
    }
}
