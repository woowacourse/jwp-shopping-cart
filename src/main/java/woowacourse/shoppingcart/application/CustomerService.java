package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.application.AuthService;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerIdRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.LoginRequest;
import woowacourse.shoppingcart.dto.LoginResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;

@Service
public class CustomerService {

    private final AuthService authService;
    private final CustomerDao customerDao;

    public CustomerService(final AuthService authService, final CustomerDao customerDao) {
        this.authService = authService;
        this.customerDao = customerDao;
    }

    public Long signUp(final SignUpRequest signUpRequest) {
        Customer customer = signUpRequest.toEntity();
        validateCustomer(customer);
        return customerDao.save(customer);
    }

    public LoginResponse login(final LoginRequest loginRequest) {
        validateExistingCustomer(loginRequest.getUserId(), loginRequest.getPassword());
        Customer customer = findCustomerByUserId(loginRequest.getUserId());
        String token = authService.createToken(customer.getId());
        return LoginResponse.of(token, customer);
    }

    public CustomerResponse findByCustomerId(final CustomerIdRequest customerIdRequest) {
        Customer customer = findCustomerById(customerIdRequest.getId());
        return CustomerResponse.from(customer);
    }

    private Customer findCustomerByUserId(final String userId) {
        return customerDao.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    private Customer findCustomerById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
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

    private void validateExistingCustomer(final String userId, final String password) {
        if (!customerDao.existCustomer(userId, password)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }
}
