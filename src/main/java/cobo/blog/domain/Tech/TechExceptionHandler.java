package cobo.blog.domain.Tech;

import cobo.blog.global.Config.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = TechController.class)
public class TechExceptionHandler extends GlobalExceptionHandler {
}
