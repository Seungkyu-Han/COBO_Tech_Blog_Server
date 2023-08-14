package cobo.blog.global.Config;

import cobo.blog.global.Repository.TechPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RedisElementInspector implements ApplicationRunner {

    private final TechPostRepository techPostRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisTemplate.opsForValue().setIfAbsent("today", "0");
        redisTemplate.opsForValue().setIfAbsent("total", "0");

        for(Integer techPostId : techPostRepository.getTechPostIdList())
            redisTemplate.opsForValue().setIfAbsent("techPost" + techPostId, "0");
    }
}
