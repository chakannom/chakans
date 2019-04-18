package com.chakans.core.util;

import javax.servlet.http.Cookie;

/**
 * Utility class for handling cookie.
 */
public final class CookieUtil {

    public static Cookie getCookie(String name, String value, Integer expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (expiry != null) {
            cookie.setMaxAge(expiry);
        }
        return cookie;
    }

    public static String getValue(Cookie[] cookies, String name) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
