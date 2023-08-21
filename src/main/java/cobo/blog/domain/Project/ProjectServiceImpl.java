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

@Service
@AllArgsConstructor
public class ProjectServiceImpl {

    private final ProjectRepository projectRepository;

    public ResponseEntity<List<ProjectProjectCardRes>> getProjectCards(Integer page, Integer size) {
        List<ProjectProjectCardRes> projectProjectCardRes = new ArrayList<>();
        for(ProjectEntity projectEntity : projectRepository.findAll(PageRequestUtil.pageRequestGenerator(page, size, Sort.Direction.DESC, "id")))
            projectProjectCardRes.add(new ProjectProjectCardRes(projectEntity));
        return new ResponseEntity<>(projectProjectCardRes, HttpStatus.OK);
    }

    public ResponseEntity<Long> getProjectCount() {
        return new ResponseEntity<>(projectRepository.count(), HttpStatus.OK);
    }

    public ResponseEntity<ProjectProjectRes> getProject(Integer projectId) {
        return new ResponseEntity<>(new ProjectProjectRes(projectRepository.findByProjectId(projectId)), HttpStatus.OK);
    }
}