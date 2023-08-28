package cobo.blog.global.Config.Jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);

        } catch (MalformedJwtException malformedJwtException) {
            setResponseStatus(response, HttpStatus.FORBIDDEN.value(),
                    "해당 AccessToken으로 인증에 실패했습니다.");
        } catch (ExpiredJwtException expiredJwtException) {
            setResponseStatus(response, HttpStatus.FORBIDDEN.value(),
                    "만료된 토큰입니다.");
        }
    }


    private void setResponseStatus(HttpServletResponse response, int sc, String message) throws IOException {
        response.setStatus(sc);
        response.setContentType("application/text");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(message);
    }
}