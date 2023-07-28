package cobo.blog.domain.Home.Data.Dto;

import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Data.Entity.ProjectSkillTagMappingEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HomeProjectRes {

    @ApiModelProperty(
            value = "프로젝트의 소개 이미지 url",
            example = "https://avatars.githubusercontent.com/u/98071131?s=400&u=9107a0b50b52da5bbc8528157eed1cca34feb3c5&v=4"
    )
    private String imgUrl;

    @ApiModelProperty(
            value = "프로젝트의 타이틀",
            example = "블로그 개발 프로젝트"
    )
    private String title;

    @ApiModelProperty(
            value = "프로필에 사용된 스킬들을 문자열",
            example = "SpringBoot"
    )
    private List<String> skillTag;

    public HomeProjectRes(ProjectEntity project) {
        this.imgUrl = project.getImgUrl();
        this.title = project.getTitle();
        this.skillTag = new ArrayList<>();
        for(ProjectSkillTagMappingEntity projectSkillTagMappingEntity : project.getProjectSkillTagMappings())
            skillTag.add(projectSkillTagMappingEntity.getSkillTag().getName());
    }
}
