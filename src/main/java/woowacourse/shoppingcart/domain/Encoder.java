package woowacourse.shoppingcart.domain;

public interface Encoder {

    String encode(String password);

    boolean matches(String oldPassword, String newPassword);
}
