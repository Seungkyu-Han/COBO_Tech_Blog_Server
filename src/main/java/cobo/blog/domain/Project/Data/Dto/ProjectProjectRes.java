package cobo.blog.domain.Project.Data.Dto;

import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Data.Entity.ProjectUserMappingEntity;
import cobo.blog.global.Util.DateConversion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectProjectRes {

    @ApiModelProperty(
            value = "프로젝트 제목",
            example = "블로그 제작 프로젝트"
    )
    private String title;

    @ApiModelProperty(
            value = "md 파일의 url",
            example = "https://cobo-blog.s3.ap-northeast-2.amazonaws.com/md/HELP.md"
    )
    private String url;

    @ApiModelProperty(
            value = "프로젝트 글 작성일자",
            example = "20230801"
    )
    private String created_at;

    private List<UserInProjectRes> users;

    public ProjectProjectRes(ProjectEntity projectEntity){
        this.title = projectEntity.getTitle();
        this.url = projectEntity.getUrl();
        this.created_at = DateConversion.DateToString(projectEntity.getCreatedAt());
        this.users = new ArrayList<>();
        for(ProjectUserMappingEntity projectUserMappingEntity : projectEntity.getProjectUserMappings())
            this.users.add(new UserInProjectRes(projectUserMappingEntity.getUser()));
    }
}
