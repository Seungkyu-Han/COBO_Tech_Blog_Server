package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(
            "SELECT u FROM UserEntity u " +
                    "WHERE u.id = :id"
    )
    UserEntity getById(Integer id);

    boolean existsByKakaoId(Integer kakaoId);
}
