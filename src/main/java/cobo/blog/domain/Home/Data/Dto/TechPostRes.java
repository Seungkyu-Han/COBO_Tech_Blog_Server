package cobo.blog.domain.Home.Data.Dto;

import cobo.blog.global.Data.Entity.TechPostEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TechPostRes {

    @ApiModelProperty(example = "TechPost 제목")
    private String title;
    @ApiModelProperty(example = "작성한 사람의 이름")
    private String user;

    @ApiModelProperty(example = "작성한 날짜를 yyyyMMdd 8자리 문자열로 응답")
    private String Date;

    public TechPostRes(TechPostEntity techPost) {
        this.title = techPost.getTitle();
        this.user = techPost.getUser().getName();
        this.Date = techPost.getCreatedAt().toString();
    }
}
