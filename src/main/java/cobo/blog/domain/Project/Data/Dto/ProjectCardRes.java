package cobo.blog.domain.Project.Data.Dto;

import cobo.blog.domain.Tech.Data.Dto.UserInTechPostRes;
import cobo.blog.global.Data.Entity.*;
import cobo.blog.global.Util.DateConversion;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectCardRes {

    private String imgUrl;

    private String title;

    private List<UserInTechPostRes> users;

    private String createdAt;

    private List<String> skillTags;

    private String content;

    public ProjectCardRes(ProjectEntity projectEntity) {
        this.imgUrl = projectEntity.getImgUrl();
        this.title = projectEntity.getTitle();
        this.createdAt = DateConversion.DateToString(projectEntity.getCreatedAt());
        this.content = projectEntity.getContent();
        this.users = new ArrayList<>();
        this.skillTags = new ArrayList<>();
        for(ProjectUserMappingEntity projectUserMappingEntity : projectEntity.getProjectUserMappings())
            this.users.add(new UserInTechPostRes(projectUserMappingEntity.getUser()));
        for(ProjectSkillTagMappingEntity projectSkillTagMappingEntity : projectEntity.getProjectSkillTagMappings())
            this.skillTags.add(projectSkillTagMappingEntity.getSkillTag().getName());
    }
}
