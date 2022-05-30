package woowacourse.auth.support;

public class AuthorizationExtractor {

    private static final String BEARER_TYPE = "Bearer";

    public static String extract(String authorizationHeader) {
        validateAuthorizationFormat(authorizationHeader);

        return authorizationHeader.substring(BEARER_TYPE.length()).trim();
    }

    private static void validateAuthorizationFormat(String authorizationHeader) {
        if (!authorizationHeader.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            throw new IllegalArgumentException("token 형식이 잘못 되었습니다. (형식: Bearer aaaaaaaa.bbbbbbbb.cccccccc)");
        }
    }
}
