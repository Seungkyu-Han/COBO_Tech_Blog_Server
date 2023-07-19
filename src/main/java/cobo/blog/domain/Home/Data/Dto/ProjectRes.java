package cobo.blog.domain.Home.Data.Dto;

import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Data.Entity.ProjectSkillTagMappingEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectRes {

    private String imgUrl;

    private String title;

    private List<String> skillTag;

    public ProjectRes(ProjectEntity project) {
        this.imgUrl = project.getImgUrl();
        this.title = project.getTitle();
        this.skillTag = new ArrayList<>();
        for(ProjectSkillTagMappingEntity projectSkillTagMappingEntity : project.getProjectSkillTagMappings())
            skillTag.add(projectSkillTagMappingEntity.getSkillTag().getName());
    }
}
