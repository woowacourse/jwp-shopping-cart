package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignUpRequest;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long signUp(final SignUpRequest signUpRequest) {
        Customer customer = signUpRequest.toEntity();
        validateCustomer(customer);
        return customerDao.save(customer);
    }

    private void validateCustomer(final Customer customer) {
        validateDuplicateUserId(customer.getUserId());
        validateDuplicateNickname(customer.getNickname());
    }

    private void validateDuplicateUserId(final String userId) {
        if (customerDao.existCustomerByUserId(userId)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    private void validateDuplicateNickname(final String nickname) {
        if (customerDao.existCustomerByNickname(nickname)) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }
}
