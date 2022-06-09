package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.PasswordChangeRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.TokenRequest;
import woowacourse.shoppingcart.repository.CustomerRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Long signUp(final CustomerRequest request) {
        return customerRepository.insert(request.toInitCustomer());
    }

    public CustomerLoginResponse login(final CustomerLoginRequest request) {
        Customer customer = customerRepository.selectByUsernameAndPassword(request.getUserId(), request.getPassword());
        return CustomerLoginResponse.ofExceptToken(customer);
    }

    public CustomerResponse findById(final TokenRequest request) {
        Customer customer = customerRepository.selectById(request.getId());
        return CustomerResponse.of(customer);
    }

    public void update(final TokenRequest tokenRequest, final CustomerUpdateRequest customerUpdateRequest) {
        Customer oldCustomer = customerRepository.selectById(tokenRequest.getId());
        Customer newCustomer = customerUpdateRequest.updatedCustomer(oldCustomer);
        customerRepository.update(newCustomer);
    }

    public void updatePassword(final TokenRequest tokenRequest, final PasswordChangeRequest passwordChangeRequest) {
        Password oldPassword = new Password(passwordChangeRequest.getOldPassword());
        Password newPassword = new Password(passwordChangeRequest.getNewPassword());
        customerRepository.updatePassword(tokenRequest.getId(), oldPassword, newPassword);
    }

    public void withdraw(final TokenRequest request) {
        customerRepository.delete(request.getId());
    }

    public void checkDuplicateUsername(final String username) {
        customerRepository.validateDuplicateUsername(username);
    }

    public void checkDuplicateNickname(final String nickname) {
        customerRepository.validateDuplicateNickname(nickname);
    }

    public void matchPassword(final TokenRequest tokenRequest, final PasswordRequest passwordRequest) {
        customerRepository.matchPassword(tokenRequest.getId(), passwordRequest.getPassword());
    }
}
