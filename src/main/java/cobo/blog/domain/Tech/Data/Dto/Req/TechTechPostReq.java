package cobo.blog.domain.Tech.Data.Dto.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TechTechPostReq {

    @ApiModelProperty(
            value = "수정할 techPost ID(PATCH 메서드만 사용합니다, POST 메서드에서는 비워주세요)",
            example = "12"
    )
    private Integer techPostId;
    @ApiModelProperty(
            value = "작성한 유저의 UserID(아마 후에 로그인 기능 생성시 삭제 예정, POST 메서드만 사용)",
            example = "4"
    )
    private Integer userId;
    @ApiModelProperty(
            value = "작성할 글의 제목",
            example = "대충 무슨무슨 글입니다."
    )
    private String title;
    @ApiModelProperty(
            value = "작성할 글의 줄거리",
            example = "앞 부분의 내용~~"
    )
    private String content;
    @ApiModelProperty(
            value = "작성할 글의 SkillTag ID 리스트",
            example = "[1, 2, 3]"
    )
    private List<Integer> skillTagIdList;

    @ApiModelProperty(
            value = "사용했던 img들의 id list",
            example = "[5, 6, 7]"
    )
    private List<Integer> fileIdList;
}
