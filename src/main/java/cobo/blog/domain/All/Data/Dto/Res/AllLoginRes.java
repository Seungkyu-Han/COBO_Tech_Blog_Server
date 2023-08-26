package cobo.blog.domain.All.Data.Dto.Res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllLoginRes {

    @ApiModelProperty(
            value = "유저의 Id",
            example = "1"
    )
    private Integer userId;

    @ApiModelProperty(
            value = "로그인 후 발급된 AccessToken",
            example = "1234Seungkyu"
    )
    private String accessToken;

    @ApiModelProperty(
            value = "로그인 후 발급된 RefreshToken",
            example = "Seungkyu1234"
    )
    private String refreshToken;
}
