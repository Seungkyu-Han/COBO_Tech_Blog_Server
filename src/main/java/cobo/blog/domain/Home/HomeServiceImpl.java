package cobo.blog.domain.Home;

import cobo.blog.domain.Home.Data.Dto.ProjectRes;
import cobo.blog.global.Data.Entity.ProjectEntity;
import cobo.blog.global.Data.Entity.ProjectSkillTagMappingEntity;
import cobo.blog.global.Repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class HomeServiceImpl {

    private final ProjectRepository projectRepository;

    public ResponseEntity<List<ProjectRes>> getProjects() {
        List<ProjectRes> projectRes = new ArrayList<>();
        for(ProjectEntity project : projectRepository.findAll()){
            List<String> skillTag = new ArrayList<>();
            for(ProjectSkillTagMappingEntity projectSkillTagMappingEntity : project.getProjectSkillTagMappings())
                skillTag.add(projectSkillTagMappingEntity.getSkillTag().getName());
            projectRes.add(new ProjectRes(project, skillTag));
        }
        return new ResponseEntity<>(projectRes, HttpStatus.OK);
    }
}
