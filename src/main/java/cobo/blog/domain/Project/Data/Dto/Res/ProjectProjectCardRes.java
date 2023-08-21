package cobo.blog.domain.Project.Data.Dto.Res;

import cobo.blog.global.Data.Entity.*;
import cobo.blog.global.Util.DateConversion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectProjectCardRes {

    @ApiModelProperty(
            value = "프로젝트 id",
            example = "1"
    )
    private Integer id;

    @ApiModelProperty(
            value = "프로젝트 이미지 url",
            example = "https://avatars.githubusercontent.com/u/98071131?s=400&u=9107a0b50b52da5bbc8528157eed1cca34feb3c5&v=4"
    )
    private String imgUrl;
    @ApiModelProperty(
            value = "프로젝트의 이름",
            example = "블로그 제작 프로젝트"
    )
    private String title;
    private List<UserInProjectRes> users;
    @ApiModelProperty(
            value = "프로젝트 날짜를 문자열로 표현",
            example = "20230711"
    )
    private String createdAt;
    @ApiModelProperty(
            value = "프로젝트에 사용한 기술들",
            example = "SpringBoot"
    )
    private List<SkillTagInProjectProjectCardRes> skillTags;
    @ApiModelProperty(
            value = "간단한 소개",
            example = "이 프로젝트는 블로그를 제작한 프로젝트입니다."
    )
    private String description;

    public ProjectProjectCardRes(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.imgUrl = projectEntity.getImgUrl();
        this.title = projectEntity.getTitle();
        this.createdAt = DateConversion.DateToString(projectEntity.getCreatedAt());
        this.description = projectEntity.getDescription();
        this.users = new ArrayList<>();
        this.skillTags = new ArrayList<>();
        for(ProjectUserMappingEntity projectUserMappingEntity : projectEntity.getProjectUserMappings())
            this.users.add(new UserInProjectRes(projectUserMappingEntity.getUser()));
        for(ProjectSkillTagMappingEntity projectSkillTagMappingEntity : projectEntity.getProjectSkillTagMappings())
            this.skillTags.add(new SkillTagInProjectProjectCardRes(projectSkillTagMappingEntity.getSkillTag()));
    }
}
