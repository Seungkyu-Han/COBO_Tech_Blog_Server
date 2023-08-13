package cobo.blog.domain.Tech.Data.Dto.Res;

import cobo.blog.global.Data.Entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInTechTechPostRes {

    @ApiModelProperty(
            value = "작성한 유저의 이름",
            example = "한승규"
    )
    private String name;
    @ApiModelProperty(
            value = "작성한 유저의 한 줄 소개",
            example = "100년 뒤 개발자 한승규입니다."
    )
    private String description;

    @ApiModelProperty(
            value = "작성한 유저의 이미지 url",
            example = "https://avatars.githubusercontent.com/u/98071131?s=400&u=9107a0b50b52da5bbc8528157eed1cca34feb3c5&v=4"
    )
    private String imgUrl;

    public UserInTechTechPostRes(UserEntity userEntity){
        this.name = userEntity.getName();
        this.description = userEntity.getDescription();
        this.imgUrl = userEntity.getImgUrl();
    }
}
