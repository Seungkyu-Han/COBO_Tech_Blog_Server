package cobo.blog.global.Data.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String img;

    private String name;

    private String email;

    private String github;

    private String description;

    private String position;

    @OneToMany(mappedBy = "user")
    private List<ProjectUserMappingEntity> projectUserMappings;

}
