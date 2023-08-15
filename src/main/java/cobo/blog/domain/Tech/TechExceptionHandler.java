package cobo.blog.domain.Tech;

import cobo.blog.global.Config.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice(basePackageClasses = TechController.class)
public class TechExceptionHandler extends GlobalExceptionHandler {
    /**
     * AWS 관련 에러
     */

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> TechIOExceptionHandler(){
        log.error("TechIOExceptionHandler: {}", this.getClass());
        return new ResponseEntity<>("S3 파일 업로드 중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> TechIllegalStateHandler(){
        log.error("TechIllegalStateHandler: {}", this.getClass());
        return new ResponseEntity<>("잘못된 인자가 전달되었습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> TechNullPointerExceptionHandler(){
        log.error("TechNullPointerExceptionHandler: {}", this.getClass());
        return new ResponseEntity<>("해당하는 값이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
