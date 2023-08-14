package cobo.blog.global.Data.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TechPostSkillTagMapping")
@NoArgsConstructor
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

    public TechPostSkillTagMappingEntity(TechPostEntity techPost, SkillTagEntity skillTag) {
        this.techPost = techPost;
        this.skillTag = skillTag;
    }
}
