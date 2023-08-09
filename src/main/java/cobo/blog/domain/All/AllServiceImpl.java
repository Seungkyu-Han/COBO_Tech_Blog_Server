package cobo.blog.domain.All;

import cobo.blog.domain.All.Data.Dto.AllHitRes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class AllServiceImpl {
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public ResponseEntity<AllHitRes> getHit(){

        Long today = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("today")));
        Long total = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("total")));

        redisTemplate.opsForValue().increment("today");

        return new ResponseEntity<>(new AllHitRes(today, today + total), HttpStatus.OK);
    }
}
