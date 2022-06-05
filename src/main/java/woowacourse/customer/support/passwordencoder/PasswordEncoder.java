package woowacourse.customer.support.passwordencoder;

public interface PasswordEncoder {
    String encode(String raw);
    boolean matches(String raw, String encoded);
}
