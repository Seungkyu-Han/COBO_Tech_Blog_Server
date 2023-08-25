package cobo.blog.global.Config;

import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class CookieSameSiteConfig {
    @Bean
    public CookieSameSiteSupplier sameSiteSupplier(){
        return CookieSameSiteSupplier.ofNone();
    }
}
