package cobo.blog.domain.Home.Data.Dto;

import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Data.Entity.ProjectSkillTagMappingEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectRes {

    @ApiModelProperty(example = "프로젝트의 소개 사진 url")
    private String imgUrl;

    @ApiModelProperty(example = "프로젝트의 타이틀")
    private String title;

    @ApiModelProperty(example = "프로필에 사용된 스킬들")
    private List<String> skillTag;

    public ProjectRes(ProjectEntity project) {
        this.imgUrl = project.getImgUrl();
        this.title = project.getTitle();
        this.skillTag = new ArrayList<>();
        for(ProjectSkillTagMappingEntity projectSkillTagMappingEntity : project.getProjectSkillTagMappings())
            skillTag.add(projectSkillTagMappingEntity.getSkillTag().getName());
    }
}
