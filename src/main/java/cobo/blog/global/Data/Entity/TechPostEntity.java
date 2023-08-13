package cobo.blog.global.Data.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "TechPost")
@NoArgsConstructor
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

    @Builder
    public TechPostEntity(String title, String url, UserEntity user){
        this.title = title;
        this.url = url;
        this.user = user;
        this.createdAt = Date.from(Instant.now());
        this.viewCount = 0L;
    }
}
