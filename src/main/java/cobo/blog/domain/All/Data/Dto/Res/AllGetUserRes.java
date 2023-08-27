package cobo.blog.domain.All.Data.Dto.Res;

import cobo.blog.global.Data.Entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AllGetUserRes {

    @ApiModelProperty(
            value = "유저의 이름",
            example = "한승규"
    )
    private String name;

    @ApiModelProperty(
            value = "깃허브 프로필 이미지",
            example = "https://avatars.githubusercontent.com/u/98071131?v=4"
    )
    private String imgUrl;

    public AllGetUserRes(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.imgUrl = userEntity.getImgUrl();
    }
}
