package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.customer.CustomerProfileResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateProfileRequest;
import woowacourse.shoppingcart.dto.customer.SignUpRequest;
import woowacourse.shoppingcart.dto.login.LoginRequest;
import woowacourse.shoppingcart.dto.login.LoginResponse;
import woowacourse.shoppingcart.entity.Customer;
import woowacourse.shoppingcart.exception.datanotfound.LoginDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotmatch.CustomerDataNotMatchException;
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
        if (customer.hasSamePassword(loginRequest.getPassword())) {
            String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
            return LoginResponse.of(token, customer);
        }
        throw new LoginDataNotFoundException("잘못된 비밀번호 입니다.");
    }

    public CustomerProfileResponse findProfile(final TokenRequest tokenRequest) {
        Customer customer = customerDao.findById(tokenRequest.getId());
        return CustomerProfileResponse.from(customer);
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
        if (!customer.hasSamePassword(customerUpdatePasswordRequest.getOldPassword())) {
            throw new CustomerDataNotMatchException("기존 비밀번호와 입력한 비밀번호가 일치하지 않습니다.");
        }
        customerDao.updatePassword(customer.getId(), customerUpdatePasswordRequest.getNewPassword());
    }

    public void withdraw(final TokenRequest tokenRequest) {
        Customer customer = customerDao.findById(tokenRequest.getId());
        customerDao.delete(customer.getId());
    }
}
