package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.TechPostEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechPostRepository extends JpaRepository<TechPostEntity, Integer> {

    List<TechPostEntity> findTop8ByOrderByIdDesc();

    @Query(
            "SELECT tp FROM TechPostEntity tp " +
                    "INNER JOIN tp.techPostSkillTagMappings tsm " +
                    "INNER JOIN tsm.skillTag st " +
                    "WHERE st.id = :skillTagId"
    )
    List<TechPostEntity> getTechPostEntitiesBySkillTagId(Integer skillTagId, Pageable pageable);

    @Query(
            "SELECT COUNT(tp) FROM TechPostEntity tp " +
                    "INNER JOIN tp.techPostSkillTagMappings tsm " +
                    "INNER JOIN tsm.skillTag st " +
                    "WHERE st.id = :skillTagId"
    )
    Long countTechPostEntitiesBySkillTagId(Integer skillTagId);

    @Query(
            "SELECT tp FROM TechPostEntity tp " +
                    "WHERE tp.id = :techPostId"
    )
    TechPostEntity findByTechPostId(Integer techPostId);

    @Query(
            "SELECT tp.id FROM TechPostEntity tp"
    )
    List<Integer> getTechPostIdList();


}
