package cobo.blog.domain.All.Data.Dto;

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

    @ApiModelProperty(
            value = "쿠키를 생성해야 하는 지 boolean",
            notes = "true면 쿠키를 생성, false면 쿠키를 생성 X"
    )
    private boolean isCookie;
}
