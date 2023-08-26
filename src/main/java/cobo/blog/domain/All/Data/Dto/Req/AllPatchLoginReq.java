package cobo.blog.domain.All.Data.Dto.Req;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AllPatchLoginReq {

    @NotNull
    @ApiModelProperty(
            value = "AccessToken을 재발급 받으려고 하는 target RefreshToken",
            example = "1234Seungkyu"
    )
    private String refreshToken;
}
