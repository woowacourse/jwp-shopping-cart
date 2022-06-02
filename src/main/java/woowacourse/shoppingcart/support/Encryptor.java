package woowacourse.shoppingcart.support;

import org.springframework.stereotype.Component;

@Component
public interface Encryptor {

    String encrypt(String plainText);
}
