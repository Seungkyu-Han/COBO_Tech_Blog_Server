package cobo.blog.global.Util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void createCookie(String name, String value, Long maxAge, HttpServletResponse httpServletResponse){
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge.intValue());
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setDomain("team-cobo.site");
        httpServletResponse.addCookie(cookie);
    }

    public static void deleteCookie(String name, HttpServletResponse httpServletResponse){
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }
}
