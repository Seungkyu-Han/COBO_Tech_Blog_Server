package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.TechPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechPostRepository extends JpaRepository<TechPostEntity, Integer> {

    List<TechPostEntity> findTop8ByOrderByIdDesc();

}
