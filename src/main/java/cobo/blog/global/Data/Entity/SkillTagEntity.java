package cobo.blog.global.Data.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "SkillTag")
public class SkillTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "skillTag")
    private List<ProjectSkillTagMappingEntity> projectSkillTagMappings;

}