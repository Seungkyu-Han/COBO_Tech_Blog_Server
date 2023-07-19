package cobo.blog.global.Data.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ProjectUserMapping")
public class ProjectUserMappingEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project")
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;
}
