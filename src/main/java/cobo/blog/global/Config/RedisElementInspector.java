package cobo.blog.global.Config;

import cobo.blog.global.Repository.TechPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RedisElementInspector implements ApplicationRunner {

    private final TechPostRepository techPostRepository;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 스프링부트 시작시에 Redis에 데이터를 넣어주는 함수
     * @Author Seungkyu-Han
     */
    @Override
    public void run(ApplicationArguments args){
        redisTemplate.opsForValue().setIfAbsent("today", "0");
        redisTemplate.opsForValue().setIfAbsent("total", "0");

        for(Integer techPostId : techPostRepository.getTechPostIdList())
            redisTemplate.opsForValue().setIfAbsent("techPost" + techPostId, "0");
    }
}
