package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.PasswordChangeRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.repository.CustomerRepository;

@Service
public class CustomerService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerRepository customerRepository;

    public CustomerService(JwtTokenProvider jwtTokenProvider,
                           final CustomerRepository customerRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerRepository = customerRepository;
    }

    public Long signUp(final CustomerRequest request) {
        return customerRepository.create(request.toInitCustomer());
    }

    public CustomerLoginResponse login(final CustomerLoginRequest request) {
        Customer customer = customerRepository.findValidUser(request.getUserId(), request.getPassword());
        String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return CustomerLoginResponse.of(customer, token);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(final TokenRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId());
        return CustomerResponse.of(customer);
    }

    public void update(final TokenRequest tokenRequest, final CustomerUpdateRequest customerUpdateRequest) {
        Customer oldCustomer = customerRepository.findById(tokenRequest.getCustomerId());
        Customer newCustomer = customerUpdateRequest.updatedCustomer(oldCustomer);
        customerRepository.update(newCustomer);
    }

    public void updatePassword(final TokenRequest tokenRequest, final PasswordChangeRequest passwordChangeRequest) {
        Password oldPassword = new Password(passwordChangeRequest.getOldPassword());
        Password newPassword = new Password(passwordChangeRequest.getNewPassword());
        customerRepository.updatePassword(tokenRequest.getCustomerId(), oldPassword, newPassword);
    }

    public void withdraw(final TokenRequest request) {
        customerRepository.delete(request.getCustomerId());
    }

    public void checkDuplicateUsername(final String username) {
        customerRepository.validateDuplicateUsername(username);
    }

    public void checkDuplicateNickname(final String nickname) {
        customerRepository.validateDuplicateNickname(nickname);
    }

    public void matchPassword(final TokenRequest tokenRequest, final PasswordRequest passwordRequest) {
        customerRepository.matchPassword(tokenRequest.getCustomerId(), passwordRequest.getPassword());
    }
}
