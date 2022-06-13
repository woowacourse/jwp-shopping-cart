package woowacourse.shoppingcart.ui;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.ForbiddenAccessException;

class AuthorizationValidator {

    AuthorizationValidator() {
    }

    void validate(Long id, Customer customer) {
        if (!customer.isSameId(id)) {
            throw new ForbiddenAccessException();
        }
    }
}
