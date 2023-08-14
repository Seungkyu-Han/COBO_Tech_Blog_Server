package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Data.Entity.TechPostSkillTagMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechPostSkillTagMappingRepository extends JpaRepository<TechPostSkillTagMappingEntity, Integer> {

    void deleteAllByTechPost(TechPostEntity techPost);
}
