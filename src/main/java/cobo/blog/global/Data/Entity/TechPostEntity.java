package cobo.blog.global.Data.Entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "TechPost")
public class TechPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @CreatedDate
    private Date createdAt;

    private String content;

    private String url;

    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;

    @OneToMany(mappedBy = "techPost")
    private List<TechPostSkillTagMappingEntity> techPostSkillTagMappings;

}
