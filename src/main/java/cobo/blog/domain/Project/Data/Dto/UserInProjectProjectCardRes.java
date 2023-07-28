package cobo.blog.domain.Project.Data.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInProjectProjectCardRes {

    @ApiModelProperty(
            value = "프로젝트에 참여한 사람의 이름",
            example = "한승규"
    )
    private String name;

    @ApiModelProperty(
            value = "참여한 사람의 프로필 이미지 url",
            example = "한승규 포뇨 이미지 url"
    )
    private String imgUrl;
}
