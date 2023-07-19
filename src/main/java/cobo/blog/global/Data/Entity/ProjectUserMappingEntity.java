package cobo.blog.global.Data.Entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ProjectUserMapping")
@Getter
public class ProjectUserMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project")
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;
}
