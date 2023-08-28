package cobo.blog.domain.Project;

import cobo.blog.global.Config.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = ProjectController.class)
public class ProjectExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> ProjectNullPointerExceptionHandler(){
        log.error("ProjectNullPointerExceptionHandler: {}", this.getClass());
        return new ResponseEntity<>("존재하지 않는 프로젝트 번호입니다.", HttpStatus.BAD_REQUEST);
    }
}
