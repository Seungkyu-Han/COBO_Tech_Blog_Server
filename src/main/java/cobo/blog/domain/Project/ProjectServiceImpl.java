package cobo.blog.domain.Project;

import cobo.blog.domain.Project.Data.Dto.Res.ProjectProjectCardRes;
import cobo.blog.domain.Project.Data.Dto.Res.ProjectProjectRes;
import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Repository.ProjectRepository;
import cobo.blog.global.Util.PageRequestUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectServiceImpl {

    private final ProjectRepository projectRepository;

    /**
     * 프로젝트의 카드를 페이지로 반환하는 함수
     * @param page 페이지의 번호
     * @param size 한 페이지 당 사이즈
     * @return 페이징 처리 한 프로젝트 카드들의 리스트
     */
    public ResponseEntity<List<ProjectProjectCardRes>> getProjectCards(Integer page, Integer size) {
        List<ProjectProjectCardRes> projectProjectCardRes = new ArrayList<>();
        for(ProjectEntity projectEntity : projectRepository.findAll(PageRequestUtil.pageRequestGenerator(page, size, Sort.Direction.DESC, "id")))
            projectProjectCardRes.add(new ProjectProjectCardRes(projectEntity));
        return new ResponseEntity<>(projectProjectCardRes, HttpStatus.OK);
    }

    /**
     * 전체 프로젝트의 개수를 반환하는 함수
     * @return 프로젝트의 개수를 Long으로 반환
     * @Author Seungkyu-Han
     */
    public ResponseEntity<Long> getProjectCount() {
        return new ResponseEntity<>(projectRepository.count(), HttpStatus.OK);
    }

    /**
     * 해당 프로젝트의 데이터를 반환하는 함수
     * @param projectId 데이터를 가져올 Project의 Id
     * @return 해당 프로젝트 데이터의 DTO
     * @Author Seungkyu-Han
     */
    public ResponseEntity<ProjectProjectRes> getProject(Integer projectId) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
        if(projectEntityOptional.isEmpty()) throw new NullPointerException();
        return new ResponseEntity<>(new ProjectProjectRes(projectEntityOptional.get()), HttpStatus.OK);
    }
}