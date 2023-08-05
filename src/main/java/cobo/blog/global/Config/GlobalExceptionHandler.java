package cobo.blog.global.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;

@Slf4j
public class GlobalExceptionHandler {

    /**
     * DB 관련 에러
     */

    @ExceptionHandler(SQLTimeoutException.class)
    public ResponseEntity<String> GlobalSQLTimeoutHandler(){
        log.info("GlobalSQLTimeoutHandler: {}", this.getClass());
        return new ResponseEntity<>("데이터베이스 접근 시간 초과", HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> GlobalSQLHandler(){
        log.info("GlobalSQLHandler: {}", this.getClass());
        return new ResponseEntity<>("데이터 베이스 관련 에러, 자세한 상황을 보고해주세요", HttpStatus.BAD_REQUEST);
    }


}
