package woowacourse.auth.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.DuplicateEmailException;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Component
@RequiredArgsConstructor
public class CustomerSpecification {

    private final CustomerDao customerDao;

    public void validateUsernameDuplicate(String username) {
        boolean existCustomerBySameUsername = customerDao.findByUsername(username).isPresent();
        if (existCustomerBySameUsername) {
            throw new DuplicateUsernameException();
        }
    }

    public void validateEmailDuplicate(String email) {
        boolean existCustomerBySameEmail = customerDao.findByEmail(email).isPresent();
        if (existCustomerBySameEmail) {
            throw new DuplicateEmailException();
        }
    }

    public void validateCustomerExists(Long id) {
        findById(id);
    }

    private Customer findById(long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }
}
