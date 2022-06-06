package woowacourse.shoppingcart.domain.Encryption;

import woowacourse.shoppingcart.domain.customer.Customer;

public interface EncryptionStrategy {

    Customer encrypt(final Customer customer);

    String encrypt(final String text);
}
