package cobo.blog.domain.Tech.Data.Dto.Req;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TechTechUpdateReq {

    @ApiModelProperty(
            value = "수정할 techPost ID",
            example = "12"
    )
    private Integer techPostId;

    @NotNull
    @ApiModelProperty(
            value = "작성할 글의 제목",
            example = "대충 무슨무슨 글입니다."
    )
    private String title;

    @NotNull
    @ApiModelProperty(
            value = "작성할 글의 줄거리",
            example = "앞 부분의 내용~~"
    )
    private String content;

    @NotNull
    @ApiModelProperty(
            value = "작성할 글의 SkillTag ID 리스트",
            example = "[1, 2, 3]"
    )
    private List<Integer> skillTagIdList;

    @NotNull
    @ApiModelProperty(
            value = "사용했던 img들의 id list",
            example = "[5, 6, 7]"
    )
    private List<Integer> fileIdList;

    @NotNull
    @ApiModelProperty(
            value = "저장할 글의 전체 내용",
            example = "안녕하세요~~"
    )
    private String detail;

    @ApiModelProperty(
            value = "삭제할 이미지 파일들의 ID List",
            example = "[1, 2, 3]"
    )
    private List<Integer> deleteFileIdList;
}
