package cobo.blog.global.Data.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TechPostSkillTagMapping")
public class TechPostSkillTagMappingEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "techPost")
    private TechPostEntity techPost;

    @ManyToOne
    @JoinColumn(name = "skillTag")
    private SkillTagEntity skillTag;
}
