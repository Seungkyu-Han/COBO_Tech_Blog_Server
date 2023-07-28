package cobo.blog.domain.About.Data.Dto;

import cobo.blog.global.Data.Entity.UserEntity;
import cobo.blog.global.Data.Enum.PositionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AboutMemberRes {

    @ApiModelProperty(
            value = "유저의 깃허브 프로필 사진 url",
            example = "https://avatars.githubusercontent.com/u/98071131?s=400&u=9107a0b50b52da5bbc8528157eed1cca34feb3c5&v=4"
    )
    private String imgUrl;

    @ApiModelProperty(
            value = "유저의 본명",
            example = "한승규"
    )
    private String name;

    @ApiModelProperty(
            value = "유저와 contact 할 수 있는 유저의 이메일",
            notes = "trust1204@gmail.com"
    )
    private String email;

    @ApiModelProperty(
            value = "유저의 깃허브",
            example = "https://github.com/Seungkyu-Han"
    )
    private String github;

    @ApiModelProperty(
            value = "유저의 한 줄 소개",
            example = "100년 뒤 개발자 한승규입니다."
    )
    private String description;

    @ApiModelProperty(
            value = "유저의 개발 Position",
            example = "BACK"
    )
    private PositionEnum position;

    public AboutMemberRes(UserEntity userEntity) {
        this.imgUrl = userEntity.getImgUrl();
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.github = userEntity.getGithub();
        this.description = userEntity.getDescription();
        this.position = userEntity.getPosition();
    }
}
