package cobo.blog.domain.All;

import cobo.blog.domain.All.Data.Dto.AllHitRes;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class AllServiceImpl {
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public ResponseEntity<AllHitRes> getHit(Integer hitCookie, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        log.info("Hit API 호출");
        log.info("요청한 IP : {}", getIp(httpServletRequest));

        Long today = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("today")));
        Long total = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("total")));

        if(hitCookie == 0) IncrementTodayAndSetCookie(httpServletRequest, httpServletResponse);

        return new ResponseEntity<>(new AllHitRes(today, today + total), HttpStatus.OK);
    }

    private String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

    private void IncrementTodayAndSetCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        redisTemplate.opsForValue().increment("today");

        Cookie cookie = new Cookie("hitCookie", "1");
        cookie.setMaxAge(900);
        httpServletResponse.addCookie(cookie);
        log.info("{} IP 조회수 + 1", httpServletRequest.getHeader("X-Forwarded-For"));
    }
}
