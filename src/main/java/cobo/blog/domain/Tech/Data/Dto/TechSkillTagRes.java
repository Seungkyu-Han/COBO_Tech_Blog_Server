package cobo.blog.domain.Tech.Data.Dto;

import cobo.blog.global.Data.Entity.SkillTagEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TechSkillTagRes {

    @ApiModelProperty(
            value = "해당 skillTag의 id(tech 글 검색용)",
            example = "1"
    )
    private Integer id;

    @ApiModelProperty(
            value = "해당 SkillTag의 name",
            example = "1"
    )
    private String name;

    public TechSkillTagRes(SkillTagEntity skillTagEntity){
        this.id = skillTagEntity.getId();
        this.name = skillTagEntity.getName();
    }
}
