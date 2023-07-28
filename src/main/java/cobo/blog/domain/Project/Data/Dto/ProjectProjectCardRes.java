package cobo.blog.domain.Project.Data.Dto;

import cobo.blog.domain.Tech.Data.Dto.UserInTechTechPostRes;
import cobo.blog.global.Data.Entity.*;
import cobo.blog.global.Util.DateConversion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectProjectCardRes {

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
    private List<UserInTechTechPostRes> users;
    @ApiModelProperty(
            value = "프로젝트 날짜를 문자열로 표현",
            example = "20230711"
    )
    private String createdAt;
    @ApiModelProperty(
            value = "프로젝트에 사용한 기술들",
            example = "SpringBoot"
    )
    private List<String> skillTags;
    @ApiModelProperty(
            value = "간단한 소개",
            example = "이 프로젝트는 블로그를 제작한 프로젝트입니다."
    )
    private String content;

    public ProjectProjectCardRes(ProjectEntity projectEntity) {
        this.imgUrl = projectEntity.getImgUrl();
        this.title = projectEntity.getTitle();
        this.createdAt = DateConversion.DateToString(projectEntity.getCreatedAt());
        this.content = projectEntity.getContent();
        this.users = new ArrayList<>();
        this.skillTags = new ArrayList<>();
        for(ProjectUserMappingEntity projectUserMappingEntity : projectEntity.getProjectUserMappings())
            this.users.add(new UserInTechTechPostRes(projectUserMappingEntity.getUser()));
        for(ProjectSkillTagMappingEntity projectSkillTagMappingEntity : projectEntity.getProjectSkillTagMappings())
            this.skillTags.add(projectSkillTagMappingEntity.getSkillTag().getName());
    }
}
