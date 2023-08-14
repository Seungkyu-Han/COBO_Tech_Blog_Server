package cobo.blog.global.Data.Entity;

import cobo.blog.domain.Tech.Data.Dto.Req.TechTechPostReq;
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

    private String fileName;

    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;

    @OneToMany(mappedBy = "techPost", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<TechPostSkillTagMappingEntity> techPostSkillTagMappings;

    @OneToMany(mappedBy = "techPost", orphanRemoval = true)
    private List<FileEntity> fileEntities;
    public TechPostEntity(String title, String content, String fileName, UserEntity user) {
        this.title = title;
        this.content = content;
        this.fileName = fileName;
        this.user = user;
        this.createdAt = Date.from(Instant.now());
        this.viewCount = 0L;
    }

    public void UpdateByTechTechPostReqAndUrl(TechTechPostReq techTechPostReq, String fileName){
        this.title = techTechPostReq.getTitle();
        this.content = techTechPostReq.getContent();
        this.fileName = fileName;
    }
}
