package cobo.blog.global.Util;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConversion {

    /**
     * Date를 문자열로 바꿔주는 함수
     * @param date 원하는 시간의 Date
     * @return yyyyMMdd에 해당하는 문자열
     * @Author Seungkyu-Han
     */
    public static String DateToString(Date date){
        return date.toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
