package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

    List<ProjectEntity> findTop6ByOrderByCreatedAtDesc();

}
