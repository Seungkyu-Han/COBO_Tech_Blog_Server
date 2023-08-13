package cobo.blog.global.Repository.Schedule;

import cobo.blog.global.Data.Entity.HitEntity;
import cobo.blog.global.Repository.HitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class RedisToDbScheduler {

    private RedisTemplate<String, String> redisTemplate;
    private HitRepository hitRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void saveHitToDB(){

        Long today = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("today")));
        Long total = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("total")));

        HitEntity hitEntity = new HitEntity(today, total + today);

        redisTemplate.opsForValue().set("total", String.valueOf(today + total));
        redisTemplate.opsForValue().set("today", String.valueOf(0));

        log.info("today: {}. total: {}", today, today + total);

        hitRepository.save(hitEntity);
    }
}