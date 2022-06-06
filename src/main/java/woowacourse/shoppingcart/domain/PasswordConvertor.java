package woowacourse.shoppingcart.domain;

public interface PasswordConvertor {

    String encode(String rawValue);

    boolean isSamePassword(String rawValue, String hashedValue);
}
