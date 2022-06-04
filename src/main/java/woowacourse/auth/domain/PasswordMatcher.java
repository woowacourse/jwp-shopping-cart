package woowacourse.auth.domain;

public interface PasswordMatcher {
    boolean isMatch(String input, String encrypted);
}
