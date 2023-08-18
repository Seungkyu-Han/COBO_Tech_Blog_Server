package cobo.blog.domain.All;

import cobo.blog.domain.All.Data.Exception.BadResponseException;
import cobo.blog.domain.All.Data.Exception.NotUserException;
import cobo.blog.global.Config.GlobalExceptionHandler;
import io.lettuce.core.RedisCommandExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.SerializationException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(BadResponseException.class)
    public ResponseEntity<String> AllBadResponseExceptionHandler(){
        log.error("AllBadResponseExceptionHandler : {}", this.getClass());
        return new ResponseEntity<>("카카오 서버로 잘못된 Token을 요청랬습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotUserException.class)
    public ResponseEntity<String> AllNotUserExceptionHandler(){
        log.error("AllNotUserExceptionHandler : {}", this.getClass());
        return new ResponseEntity<>("허용된 사용자가 아닙니다.", HttpStatus.UNAUTHORIZED);
    }
}
