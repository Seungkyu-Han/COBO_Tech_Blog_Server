package cobo.blog.domain.All.Data.Dto.Req;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AllDeleteLoginReq {

    @NotNull
    @ApiModelProperty(
            value = "로그아웃 하려고 하는 유저의 RefreshToken",
            example = "1234Seungkyu"
    )
    private String refreshToken;
}
