package cobo.blog.domain.Home;

import cobo.blog.global.Config.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = HomeController.class)
public class HomeExceptionHandler extends GlobalExceptionHandler {
}
