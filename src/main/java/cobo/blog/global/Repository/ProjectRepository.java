package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

    List<ProjectEntity> findTop6ByOrderByIdDesc();

    @Query("SELECT p FROM ProjectEntity p " +
            "JOIN p.projectUserMappings us " +
            "JOIN us.user u "+
            "WHERE p.id = :projectId")
    ProjectEntity findByProjectId(Integer projectId);

}
