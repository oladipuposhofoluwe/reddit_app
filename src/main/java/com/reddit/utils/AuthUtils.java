package com.reddit.utils;//package com.reddit.app.utils;

import org.apache.commons.lang3.StringUtils;

public class AuthUtils {
    public static boolean isEmptyToken(String token) {
        return StringUtils.isEmpty(token);
    }
}
