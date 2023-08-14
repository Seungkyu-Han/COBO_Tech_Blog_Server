package cobo.blog.domain.Tech.Data.Dto.Req;

import cobo.blog.global.Data.Entity.SkillTagEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TechTechPostReq {

    private Integer userId;
    private String title;
    private String content;
    private List<Integer> skillTagIdList;
}
