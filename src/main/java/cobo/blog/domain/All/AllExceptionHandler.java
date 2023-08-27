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

import java.io.IOException;
import java.net.ProtocolException;

@Slf4j
@RestControllerAdvice(basePackageClasses = AllController.class)
public class AllExceptionHandler extends GlobalExceptionHandler {

    /**
     * Redis 관련 에러
     */

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<String> AllRedisConnectionFailureExceptionHandler(){
        log.error("AllRedisConnectionFailureHandler: {}", this.getClass());
        return new ResponseEntity<>("현재 Redis 접속 불가입니다.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(RedisCommandExecutionException.class)
    public ResponseEntity<String> AllRedisCommandExecutionExceptionHandler(){
        log.error("AllRedisCommandExecutionHandler: {}", this.getClass());
        return new ResponseEntity<>("Redis 명령 실행 중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SerializationException.class)
    public ResponseEntity<String> AllSerializationExceptionHandler(){
        log.error("AllSerializationHandler: {}", this.getClass());
        return new ResponseEntity<>("Redis 직렬화 도중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * KAKAO 로그인 관련 에러
     */

    @ExceptionHandler(ProtocolException.class)
    public ResponseEntity<String> AllProtocolExceptionHandler(){
        log.error("AllProtocolExceptionHandler : {}", this.getClass());
        return new ResponseEntity<>("KAKAO URL 연결 중 에러가 발생했습니다", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> AllIOExceptionHandler(){
        log.error("AllIOExceptionHandler : {}", this.getClass());
        return new ResponseEntity<>("해당 code로 카카오 인증에 실패했습니다.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> AllNullPointerExceptionHandler(){
        log.error("AllNullPointerExceptionHandler : {}", this.getClass());
        return new ResponseEntity<>("Request 한 데이터가 서버에 존재하지 않습니다.", HttpStatus.FORBIDDEN);
    }
}
