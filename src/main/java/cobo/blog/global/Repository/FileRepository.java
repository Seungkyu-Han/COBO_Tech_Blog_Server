package cobo.blog.global.Repository;

import cobo.blog.global.Data.Entity.FileEntity;
import cobo.blog.global.Data.Entity.TechPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    List<FileEntity> findAllByTechPost(TechPostEntity techPostEntity);

    void deleteAllByTechPost(TechPostEntity techPostEntity);
}
