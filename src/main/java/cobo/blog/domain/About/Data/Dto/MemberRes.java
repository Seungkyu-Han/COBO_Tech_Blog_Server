package cobo.blog.domain.About.Data.Dto;

import cobo.blog.global.Data.Entity.UserEntity;
import cobo.blog.global.Data.Enum.PositionEnum;
import lombok.Data;

@Data
public class MemberRes {

    private String imgUrl;

    private String name;

    private String email;

    private String github;

    private String description;

    private PositionEnum position;

    public MemberRes(UserEntity userEntity) {
        this.imgUrl = userEntity.getImgUrl();
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.github = userEntity.getGithub();
        this.description = userEntity.getDescription();
        this.position = userEntity.getPosition();
    }
}
