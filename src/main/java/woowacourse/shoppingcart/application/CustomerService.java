package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateProfileRequest;
import woowacourse.shoppingcart.dto.LoginRequest;
import woowacourse.shoppingcart.dto.LoginResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.TokenRequest;
import woowacourse.shoppingcart.entity.Customer;
import woowacourse.shoppingcart.exception.duplicateddata.CustomerDuplicatedDataException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomerService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Long signUp(final SignUpRequest signUpRequest) {
        Customer customer = signUpRequest.toEntity();
        validateNotDuplicateUserId(customer.getUserId());
        validateNotDuplicateNickname(customer.getNickname());
        return customerDao.save(customer);
    }

    private void validateNotDuplicateUserId(final String userId) {
        if (customerDao.existCustomerByUserId(userId)) {
            throw new CustomerDuplicatedDataException("이미 존재하는 아이디입니다.");
        }
    }

    private void validateNotDuplicateNickname(final String nickname) {
        if (customerDao.existCustomerByNickname(nickname)) {
            throw new CustomerDuplicatedDataException("이미 존재하는 닉네임입니다.");
        }
    }

    public LoginResponse login(final LoginRequest loginRequest) {
        Customer customer = customerDao.findByUserId(loginRequest.getUserId());
        String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return LoginResponse.of(token, customer);
    }

    public CustomerResponse findProfile(final TokenRequest tokenRequest) {
        Customer customer = customerDao.findById(tokenRequest.getId());
        return CustomerResponse.from(customer);
    }

    @Transactional
    public void updateProfile(final TokenRequest tokenRequest,
                              final CustomerUpdateProfileRequest customerUpdateProfileRequest) {
        validateNotDuplicateNickname(customerUpdateProfileRequest.getNickname());
        Customer customer = customerDao.findById(tokenRequest.getId());
        customerDao.update(customer.getId(), customerUpdateProfileRequest.getNickname());
    }

    @Transactional
    public void updatePassword(final TokenRequest tokenRequest,
                               final CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        Customer customer = customerDao.findById(tokenRequest.getId());
        customerDao.updatePassword(customer.getId(), customerUpdatePasswordRequest.getNewPassword());
    }

    public void withdraw(final TokenRequest tokenRequest) {
        Customer customer = customerDao.findById(tokenRequest.getId());
        customerDao.delete(customer.getId());
    }
}
