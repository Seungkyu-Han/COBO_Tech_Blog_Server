package cobo.blog.domain.About;

import cobo.blog.global.Config.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = AboutController.class)
public class AboutExceptionHandler extends GlobalExceptionHandler {
}
