package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.SkillTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillTagRepository extends JpaRepository<SkillTagEntity, Integer> {

    /**
     * SkillTagIdList에 있는 ID로 SkillTag를 List로 가져옴
     * @param skillTagIdList 데이터를 가져오고 싶은 SkillTag들의 Id 리스트
     * @return Id에 해당하는 SkillTag들의 리스트
     * @Author Seungkyu-Han
     */
    @Query(
            "SELECT st FROM SkillTagEntity st " +
                    "WHERE st.id IN :skillTagIdList"
    )
    List<SkillTagEntity> getSkillTagEntitiesByIdList(List<Integer> skillTagIdList);
}
