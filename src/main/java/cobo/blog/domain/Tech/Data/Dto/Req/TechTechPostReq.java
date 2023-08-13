package cobo.blog.domain.Tech.Data.Dto.Req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TechTechPostReq {

    private Integer userId;
    private String title;
}
