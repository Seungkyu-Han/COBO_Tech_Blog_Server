package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Data.Entity.TechPostSkillTagMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechPostSkillTagMappingRepository extends JpaRepository<TechPostSkillTagMappingEntity, Integer> {

    /**
     * TechPost에 해당하는 Mapping 들을 모두 삭제하는 함수
     * @param techPostEntity mapping된 techPostEntity
     * @Author Seungkyu-Han
     */
    void deleteAllByTechPost(TechPostEntity techPostEntity);
}
