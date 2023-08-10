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
                    "WHERE st.id = :skillTag"
    )
    List<TechPostEntity> getTechPostEntitiesBySkillTagId(Integer skillTag, Pageable pageable);
}
