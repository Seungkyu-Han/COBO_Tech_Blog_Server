package cobo.blog.domain.About.Data.Dto;

import cobo.blog.global.Data.Entity.UserEntity;
import cobo.blog.global.Data.Enum.PositionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberRes {

    @ApiModelProperty(example = "유저의 깃허브 프로필 사진 url")
    private String imgUrl;

    @ApiModelProperty(example = "유저의 본명")
    private String name;

    @ApiModelProperty(example = "유저와 contact 할 수 있는 유저의 이메일")
    private String email;

    @ApiModelProperty(example = "유저의 깃허브")
    private String github;

    @ApiModelProperty(example = "유저의 한 줄 소개")
    private String description;

    @ApiModelProperty(example = "유저의 개발 Position")
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
