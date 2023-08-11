package cobo.blog.global.Util;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConversion {

    public static String DateToString(Date date){
        return date.toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
