package cobo.blog.domain.Project;

import cobo.blog.domain.Project.Data.Dto.ProjectCardRes;
import cobo.blog.domain.Tech.Data.Dto.UserInTechPostRes;
import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Data.Entity.ProjectUserMappingEntity;
import cobo.blog.global.Data.Entity.UserEntity;
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

    public ResponseEntity<List<ProjectCardRes>> getProjectCards() {
        List<ProjectCardRes> projectCardRes = new ArrayList<>();
        for(ProjectEntity projectEntity : projectRepository.findAll())
            projectCardRes.add(new ProjectCardRes(projectEntity));
        return new ResponseEntity<>(projectCardRes, HttpStatus.OK);
    }
}
