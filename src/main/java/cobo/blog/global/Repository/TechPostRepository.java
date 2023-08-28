package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.TechPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechPostRepository extends JpaRepository<TechPostEntity, Integer> {

    /**
     * 최근에 작성된 TechPost 8개를 리스트로 반환해주는 함수
     * @return 가장 최근에 작성된 TechPostEntity 8개
     * @Author Seungkyu-Han
     */
    List<TechPostEntity> findTop8ByOrderByIdDesc();

    /**
     * SkillTag로 페이징처리 하여 TechPost를 리스트로 가져오는 함수
     * @param skillTagId 검색하고 싶은 SkillTag의 Id
     * @param pageable 페이지의 정보를 담고 있는 pageable
     * @return 해당 SkillTag를 가지고 있는 페이징 처리 한 TechPost의 리스트
     * @Author Seungkyu-Han
     */
    @Query(
            "SELECT tp FROM TechPostEntity tp " +
                    "INNER JOIN tp.techPostSkillTagMappings tsm " +
                    "INNER JOIN tsm.skillTag st " +
                    "WHERE st.id = :skillTagId"
    )
    List<TechPostEntity> getTechPostEntitiesBySkillTagId(Integer skillTagId, Pageable pageable);


    /**
     * 해당 스킬태그에 해당하는 TechPost의 개수를 반환하는 함수
     * @param skillTagId 검색하고 싶은 SkillTag의 Id
     * @return 해당 SkillTag를 가지고 있는 TechPostEntity
     * @Author Seungkyu-Han
     */
    @Query(
            "SELECT COUNT(tp) FROM TechPostEntity tp " +
                    "INNER JOIN tp.techPostSkillTagMappings tsm " +
                    "INNER JOIN tsm.skillTag st " +
                    "WHERE st.id = :skillTagId"
    )
    Long countBySkillTagId(Integer skillTagId);

    /**
     * 현재 존재하는 TechPost의 ID를 모두 제공해주는 함수
     * @return 모든 TechPost의 ID 리스트
     * @Author Seungkyu-Han
     */
    @Query(
            "SELECT tp.id FROM TechPostEntity tp"
    )
    List<Integer> getTechPostIdList();
}
