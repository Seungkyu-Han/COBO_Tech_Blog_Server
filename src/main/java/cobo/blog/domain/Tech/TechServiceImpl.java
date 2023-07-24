package cobo.blog.domain.Tech;

import cobo.blog.domain.Tech.Data.Dto.TechPostRes;
import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Repository.TechPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TechServiceImpl {

    private final TechPostRepository techPostRepository;

    public ResponseEntity<List<TechPostRes>> getPosts(Integer page, Integer size) {
        List<TechPostRes> techPostRes = new ArrayList<>();
        for(TechPostEntity techPostEntity : this.getTechPostEntitiesWithPaging(page, size))
            techPostRes.add(new TechPostRes(techPostEntity));
        return new ResponseEntity<>(techPostRes, HttpStatus.OK);
    }

    public Page<TechPostEntity> getTechPostEntitiesWithPaging(int page, int size){
        return techPostRepository.findAll(PageRequest.of(
                page - 1, size, Sort.by(Sort.Direction.DESC, "id")
                ));
    }
}
