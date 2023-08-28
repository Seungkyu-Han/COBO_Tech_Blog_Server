package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * userId를 이용하여 UserEntity를 가져오는 함수
     * @param id 가져오고 싶은 User의 userId
     * @return userId에 해당하는 UserEntity를 Optional로 감싸서 반환
     * @Author Seungkyu-Han
     */
    Optional<UserEntity> findById(Integer id);


    /**
     * kakaoId를 이용하여 UserEntity를 가져오는 함수
     * @param kakaoId 가져오고 싶은 User의 kakaoId
     * @return kakaoId에 해당하는 UserEntity
     * @Author Seungkyu-Han
     */
    UserEntity findByKakaoId(Integer kakaoId);
}
