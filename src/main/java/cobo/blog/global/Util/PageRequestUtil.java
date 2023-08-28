package cobo.blog.global.Util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


public class PageRequestUtil {

    /**
     * JPA에 사용할 PageRequest를 만들어주는 함수
     * @param page 가져올 페이지의 번호
     * @param size 한 페이지에 넣을 데이터의 개수
     * @param order 오름차순, 내림차순
     * @param properties 정렬할 데이터 column의 이름
     * @return 페이징으로 만든 PageRequest
     * @Author Seungkyu-Han
     */
    public static PageRequest pageRequestGenerator(int page, int size, Sort.Direction order, String properties){
        return PageRequest.of(
                page - 1, size, Sort.by(order, properties)
        );
    }
}