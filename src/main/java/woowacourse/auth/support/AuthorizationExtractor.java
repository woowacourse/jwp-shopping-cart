package woowacourse.auth.support;

public class AuthorizationExtractor {
    public static String BEARER_TYPE = "Bearer";

    public static String extract(String value) {
        if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
            String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
            int commaIndex = authHeaderValue.indexOf(',');
            if (commaIndex > 0) {
                authHeaderValue = authHeaderValue.substring(0, commaIndex);
            }
            return authHeaderValue;
        }

        return null;
    }
}
