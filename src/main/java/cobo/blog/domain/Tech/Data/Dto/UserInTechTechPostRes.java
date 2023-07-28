package cobo.blog.domain.Tech.Data.Dto;

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

    public UserInTechTechPostRes(UserEntity userEntity){
        this.name = userEntity.getName();
        this.description = userEntity.getDescription();
    }
}
