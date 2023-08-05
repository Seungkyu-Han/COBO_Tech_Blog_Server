package cobo.blog.global.Data.Entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imgUrl;

    private String title;

    @CreatedDate
    private Date createdAt;

    private String content;

    private String url;

    @OneToMany(mappedBy = "user")
    private List<ProjectUserMappingEntity> projectUserMappings;

    @OneToMany(mappedBy = "project")
    private List<ProjectSkillTagMappingEntity> projectSkillTagMappings;
}
