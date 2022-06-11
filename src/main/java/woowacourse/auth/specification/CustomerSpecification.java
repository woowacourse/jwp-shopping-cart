package woowacourse.auth.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.exception.badrequest.DuplicateEmailException;
import woowacourse.shoppingcart.exception.badrequest.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.notfound.InvalidCustomerException;

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

    public void validateCustomerExists(long id) {
        customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

}
