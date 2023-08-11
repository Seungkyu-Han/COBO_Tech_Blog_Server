package cobo.blog.global.Util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


public class PageRequestUtil {

    public static PageRequest pageRequestGenerator(int page, int size, Sort.Direction order, String properties){
        return PageRequest.of(
                page - 1, size, Sort.by(order, properties)
        );
    }
}