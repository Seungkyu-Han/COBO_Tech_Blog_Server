package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.SkillTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillTagRepository extends JpaRepository<SkillTagEntity, Integer> {
}
