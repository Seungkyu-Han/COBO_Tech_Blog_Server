package cobo.blog.domain.Project;

import cobo.blog.global.Config.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = ProjectController.class)
public class ProjectExceptionHandler extends GlobalExceptionHandler {
}
