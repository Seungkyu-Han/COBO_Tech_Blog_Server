package cobo.blog.domain.All;

import cobo.blog.domain.All.Data.Dto.Req.AllDeleteLoginReq;
import cobo.blog.domain.All.Data.Dto.Req.AllPatchLoginReq;
import cobo.blog.domain.All.Data.Dto.Res.AllGetUserRes;
import cobo.blog.domain.All.Data.Dto.Res.AllHitRes;
import cobo.blog.domain.All.Data.Dto.Res.AllLoginRes;
import cobo.blog.global.Config.Jwt.JwtTokenProvider;
import cobo.blog.global.Data.Entity.UserEntity;
import cobo.blog.global.Repository.UserRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@RequiredArgsConstructor
public class AllServiceImpl {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${kakao.auth.client_id}")
    private String client_id;
    @Value("${kakao.auth.redirect_uri}")
    private String redirect_uri;
    @Value("${jwt.secret.key}")
    private String secretKey;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public ResponseEntity<AllHitRes> getHit(Boolean isCount){

        if(isCount)
            redisTemplate.opsForValue().increment("today");

        Long today = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("today")));
        Long total = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("total")));

        return new ResponseEntity<>(new AllHitRes(today, today + total), HttpStatus.OK);
    }

    public ResponseEntity<AllLoginRes> login(String code) throws IOException{

        Integer userId = getKakaoUserIdByKakaoAccessToken(getKakaoAccessToken(code));

        String accessToken = jwtTokenProvider.createAccessToken(userId, secretKey);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId, secretKey);

        redisTemplate.opsForValue().set(refreshToken, userId.toString(), 14, TimeUnit.DAYS);

        return new ResponseEntity<>(new AllLoginRes(userId, accessToken, refreshToken), HttpStatus.OK);
    }

    public ResponseEntity<AllLoginRes> login(AllPatchLoginReq allPatchLoginReq) {
        Integer userId = Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(allPatchLoginReq.getRefreshToken())));

        String accessToken = jwtTokenProvider.createAccessToken(userId, secretKey);

        return new ResponseEntity<>(new AllLoginRes(userId, accessToken, allPatchLoginReq.getRefreshToken()), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> logout(AllDeleteLoginReq allDeleteLoginReq){

        redisTemplate.delete(allDeleteLoginReq.getRefreshToken());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<AllGetUserRes> check(Authentication authentication) {
        Integer userId = Integer.valueOf(authentication.getName());

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if(userEntityOptional.isEmpty())
            throw new NullPointerException();
        return new ResponseEntity<>(new AllGetUserRes(userEntityOptional.get()), HttpStatus.OK);
    }

    private String getKakaoAccessToken(String code) throws IOException {
        String access_token;
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


        httpURLConnection.getResponseCode();

        JsonElement element = getJsonElement(httpURLConnection);

        access_token = element.getAsJsonObject().get("access_token").getAsString();

        bufferedWriter.close();

        return access_token;
    }

    private Integer getKakaoUserIdByKakaoAccessToken(String token) throws IOException {
        JsonElement element = getJsonElementByAccessToken(token);
        Integer id = element.getAsJsonObject().get("id").getAsInt();

        log.info("로그인 시도하는 유저의 KAKAO ID : {}", id);

        return userRepository.findByKakaoId(id).getId();
    }

    private JsonElement getJsonElementByAccessToken(String token) throws IOException {
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);

        return getJsonElement(httpURLConnection);
    }


    private JsonElement getJsonElement(HttpURLConnection httpURLConnection) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();

        while((line = bufferedReader.readLine()) != null){
            result.append(line);
        }

        bufferedReader.close();

        return JsonParser.parseString(result.toString());
    }
}