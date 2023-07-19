package cobo.blog.global.Data.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ProjectSkillTagMapping")
public class ProjectSkillTagMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project")
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "skillTag")
    private SkillTagEntity skillTag;
}
