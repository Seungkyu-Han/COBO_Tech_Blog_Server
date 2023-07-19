package cobo.blog.domain.Home.Data.Dto;

import cobo.blog.global.Data.Entity.TechPostEntity;
import lombok.Data;

@Data
public class TechPostRes {

    private String title;
    private String user;
    private String Date;

    public TechPostRes(TechPostEntity techPost) {
        this.title = techPost.getTitle();
        this.user = techPost.getUser().getName();
        this.Date = techPost.getCreatedAt().toString();
    }
}
