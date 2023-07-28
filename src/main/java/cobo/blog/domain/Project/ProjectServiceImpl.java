package cobo.blog.domain.Project;

import cobo.blog.domain.Project.Data.Dto.ProjectProjectCardRes;
import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl {

    private final ProjectRepository projectRepository;

    public ResponseEntity<List<ProjectProjectCardRes>> getProjectCards() {
        List<ProjectProjectCardRes> projectProjectCardRes = new ArrayList<>();
        for(ProjectEntity projectEntity : projectRepository.findAll())
            projectProjectCardRes.add(new ProjectProjectCardRes(projectEntity));
        return new ResponseEntity<>(projectProjectCardRes, HttpStatus.OK);
    }
}
