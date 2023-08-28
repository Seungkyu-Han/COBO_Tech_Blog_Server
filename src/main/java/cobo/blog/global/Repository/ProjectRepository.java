package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

    /**
     * 최근에 작성한 Project 8개를 가져오는 함수
     * @return 최근에 작성한 Project 8개 List
     * @Author Seungkyu-Han
     */
    List<ProjectEntity> findTop6ByOrderByIdDesc();
}
