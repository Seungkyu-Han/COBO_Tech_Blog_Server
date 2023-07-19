package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.ProjectUserMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUserMappingRepository extends JpaRepository<ProjectUserMappingEntity, Integer> {
}
