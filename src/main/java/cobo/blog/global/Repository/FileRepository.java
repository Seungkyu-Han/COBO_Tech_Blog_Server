package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.FileEntity;
import cobo.blog.global.Data.Entity.TechPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    List<FileEntity> findAllByTechPost(TechPostEntity techPostEntity);

    /**
     * TechPost에 관련된 모든 파일을 삭제하는 함수
     * @param techPostEntity 삭제하려는 File과 관련된 TechPost
     * @Author Seungkyu-Han
     */
    void deleteAllByTechPost(TechPostEntity techPostEntity);
}
