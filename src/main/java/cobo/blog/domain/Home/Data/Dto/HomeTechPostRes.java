package cobo.blog.domain.Home.Data.Dto;

import cobo.blog.global.Data.Entity.TechPostEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HomeTechPostRes {

    @ApiModelProperty(
            value = "TechPost의 Id",
            example = "1"
    )
    private Integer techPostId;

    @ApiModelProperty(
            value = "TechPost 제목",
            example = "한승규의 스프링 고민"
    )
    private String title;
    @ApiModelProperty(
            value = "작성한 사람의 이름",
            example = "한승규"
    )
    private String user;

    @ApiModelProperty(
            value = "작성한 날짜를 yyyyMMdd 8자리 문자열로 응답",
            example = "20230711"
    )
    private String Date;


    public HomeTechPostRes(TechPostEntity techPost) {
        this.techPostId = techPost.getId();
        this.title = techPost.getTitle();
        this.user = techPost.getUser().getName();
        this.Date = techPost.getCreatedAt().toString();
    }
}
