package cobo.blog.global.Data.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "hit")
@NoArgsConstructor
@Data
public class HitEntity {

    @Id
    private String date;

    private Long today;

    private Long total;

    public HitEntity(Long today, Long total) {
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.today = today;
        this.total = total;
    }

}
