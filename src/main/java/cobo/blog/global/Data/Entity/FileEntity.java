package cobo.blog.global.Data.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Data
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "techPost")
    private TechPostEntity techPost;

    public FileEntity(String fileName){
        this.fileName = fileName;
    }
}
