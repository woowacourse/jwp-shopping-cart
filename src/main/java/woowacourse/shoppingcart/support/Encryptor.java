package woowacourse.shoppingcart.support;

import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.PlainPassword;

public interface Encryptor {

    Password encrypt(PlainPassword plainPassword);
}
