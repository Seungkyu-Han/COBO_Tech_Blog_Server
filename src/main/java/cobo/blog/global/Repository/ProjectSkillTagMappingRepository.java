package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.ProjectSkillTagMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectSkillTagMappingRepository extends JpaRepository<ProjectSkillTagMappingEntity, Integer> {
}
