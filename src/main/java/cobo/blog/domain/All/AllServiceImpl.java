package cobo.blog.domain.All;

import cobo.blog.domain.All.Data.Dto.AllHitRes;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AllServiceImpl {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${kakao.auth.client_id}")
    private String client_id;
    @Value("${kakao.auth.redirect_uri}")
    private String redirect_uri;

    @Transactional
    public ResponseEntity<AllHitRes> getHit(Integer hitCookie, HttpServletResponse httpServletResponse){


        if(hitCookie == 0) IncrementTodayAndSetCookie(httpServletResponse);

        Long today = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("today")));
        Long total = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("total")));

        return new ResponseEntity<>(new AllHitRes(today, today + total), HttpStatus.OK);
    }

    public String getKakaoAccessToken(String code) throws IOException {
        String access_token;
        String refresh_token;
        String reqURL = "https://kauth.kakao.com/oauth/token";

        URL url = new URL(reqURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
        String stringBuilder = "grant_type=authorization_code" +
                "&client_id=" + client_id +
                "&redirect_uri=" + redirect_uri +
                "&code=" + code;
        bufferedWriter.write(stringBuilder);
        bufferedWriter.flush();


        log.info(httpURLConnection.getURL().toString());
        int responseCode = httpURLConnection.getResponseCode();
        log.info("responseCode : {}", responseCode);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();

        while((line = bufferedReader.readLine()) != null){
            result.append(line);
        }
        log.info("response body : {}", result);

        JsonElement element = JsonParser.parseString(result.toString());

        access_token = element.getAsJsonObject().get("access_token").getAsString();
        refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

        log.info("access_token : {}", access_token);
        log.info("refresh_token : {}", refresh_token);

        bufferedReader.close();
        bufferedWriter.close();

        return access_token;
    }

    private void IncrementTodayAndSetCookie(HttpServletResponse httpServletResponse){
        redisTemplate.opsForValue().increment("today");

        Cookie cookie = new Cookie("hitCookie", "1");
        cookie.setMaxAge(900);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }
}
