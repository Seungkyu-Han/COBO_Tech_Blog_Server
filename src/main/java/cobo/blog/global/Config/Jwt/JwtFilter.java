package cobo.blog.global.Config.Jwt;

import cobo.blog.global.Config.Jwt.Exception.EmptyAuthorizationException;
import cobo.blog.global.Config.Jwt.Exception.NotAuthorizationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret.key}")
    private String secretKey;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(!isAuthPath(request.getServletPath())){
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorization == null || !authorization.startsWith("Bearer "))
            throw new EmptyAuthorizationException("Empty Authorization");

        String token = authorization.split(" ")[1];

        if(!jwtTokenProvider.isAccessToken(token, secretKey))
            throw new NotAuthorizationException("Not AccessToken");

        Integer userId = jwtTokenProvider.getUserId(token, secretKey);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);
    }

    private boolean isAuthPath(String path){
        return path.startsWith("/api/all/check") ||
                path.startsWith("/api/tech/post") ||
                path.startsWith("/api/tech/img");
    }
}
