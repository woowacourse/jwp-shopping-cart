package woowacourse.auth.support;

public interface PasswordEncoder {

    String encode(final String password);

    boolean isMatchPassword(final String encodedPassword, final String password);
}
