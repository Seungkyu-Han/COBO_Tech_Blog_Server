package cobo.blog.global.Config.Jwt;

import cobo.blog.global.Util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import static cobo.blog.global.Util.CookieUtil.createCookie;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            log.info("Header: {}", headerName);
        }

        if(!isAuthPath(request.getServletPath())){
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = getCookieValue(request, "AccessToken");

        if(accessToken == null){
            String refreshToken = getCookieValue(request, "RefreshToken");
            if(refreshToken != null && jwtTokenProvider.isRefreshToken(refreshToken, secretKey)){
                Integer userId = jwtTokenProvider.getUserId(refreshToken, secretKey);
                if(refreshToken.equals(redisTemplate.opsForValue().get("RefreshToken" + userId))){
                    createCookie("AccessToken", jwtTokenProvider.createAccessToken(userId, secretKey), 900L, response);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        else{
            Integer userId = jwtTokenProvider.getUserId(accessToken, secretKey);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isAuthPath(String path){
        return path.startsWith("/api/all/check") ||
                path.startsWith("/api/tech/post") ||
                path.startsWith("/api/tech/img") ||
                path.startsWith("/api/all/login");
    }

    private String getCookieValue(HttpServletRequest httpServletRequest, String name) {
        if (httpServletRequest.getCookies() != null)
            for (Cookie cookie : httpServletRequest.getCookies())
                if (cookie.getName().equals(name))
                    return cookie.getValue();
        return null;
    }
}
