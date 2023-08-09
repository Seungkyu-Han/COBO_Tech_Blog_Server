package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.HitEntity;
import cobo.blog.global.Data.Entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HitRepository extends JpaRepository<HitEntity, String> {

}
