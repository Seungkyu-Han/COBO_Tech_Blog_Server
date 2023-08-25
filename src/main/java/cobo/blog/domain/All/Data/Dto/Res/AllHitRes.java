package cobo.blog.domain.All.Data.Dto.Res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllHitRes {

    @ApiModelProperty(
            value = "블로그 오늘 조회수",
            example = "1"
    )
    private Long today;

    @ApiModelProperty(
            value = "블로그 전체 조회수",
            example = "1020303"
    )
    private Long total;
}
