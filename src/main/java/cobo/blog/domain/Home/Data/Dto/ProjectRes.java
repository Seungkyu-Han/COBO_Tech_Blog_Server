package cobo.blog.domain.Home.Data.Dto;

import cobo.blog.global.Data.Entity.ProjectEntity;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRes {

    private String imgUrl;

    private String title;

    private List<String> skillTag;

    public ProjectRes(ProjectEntity project, List<String> skillTag) {
        this.imgUrl = project.getImg();
        this.title = project.getTitle();
        this.skillTag = skillTag;
    }
}
