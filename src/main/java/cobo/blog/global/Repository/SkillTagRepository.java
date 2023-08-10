package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.SkillTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillTagRepository extends JpaRepository<SkillTagEntity, Integer> {
}
