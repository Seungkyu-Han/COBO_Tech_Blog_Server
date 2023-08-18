package cobo.blog.global.Data.Entity;

import cobo.blog.global.Data.Enum.PositionEnum;
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

    private String imgUrl;

    private String name;

    private String email;

    private String github;

    private String description;

    private Integer kakaoId;

    @Enumerated(EnumType.STRING)
    private PositionEnum position;

    @OneToMany(mappedBy = "user")
    private List<ProjectUserMappingEntity> projectUserMappings;

    @OneToMany(mappedBy = "user")
    private List<TechPostEntity> techPosts;
}
