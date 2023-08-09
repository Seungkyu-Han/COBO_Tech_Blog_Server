package cobo.blog.domain.All;

import cobo.blog.global.Config.GlobalExceptionHandler;
import io.lettuce.core.RedisCommandExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.SerializationException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLTimeoutException;

@Slf4j
@RestControllerAdvice(basePackageClasses = AllController.class)
public class AllExceptionHandler extends GlobalExceptionHandler {

    /**
     * Redis 관련 에러
     */

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<String> AllRedisConnectionFailureHandler(){
        log.info("AllRedisConnectionFailureHandler: {}", this.getClass());
        return new ResponseEntity<>("현재 Redis 접속 불가입니다.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(RedisCommandExecutionException.class)
    public ResponseEntity<String> AllRedisCommandExecutionHandler(){
        log.info("AllRedisCommandExecutionHandler: {}", this.getClass());
        return new ResponseEntity<>("Redis 명령 실행 중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SerializationException.class)
    public ResponseEntity<String> AllSerializationHandler(){
        log.info("AllSerializationHandler: {}", this.getClass());
        return new ResponseEntity<>("Redis 직렬화 도중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
