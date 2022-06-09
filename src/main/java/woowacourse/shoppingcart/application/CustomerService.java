package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.customer.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.customer.CustomerPasswordRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateProfileRequest;
import woowacourse.shoppingcart.exception.duplicateddata.CustomerDuplicatedDataException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomerService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Long signUp(final CustomerSignUpRequest customerSignUpRequest) {
        Customer customer = customerSignUpRequest.toEntity();
        validateNotDuplicateUserId(customer.getUserId());
        validateNotDuplicateNickname(customer.getNickname());
        return customerDao.save(customer);
    }

    private void validateNotDuplicateUserId(final String userId) {
        if (customerDao.existCustomerByUserId(userId)) {
            throw new CustomerDuplicatedDataException("이미 가입된 이메일입니다.");
        }
    }

    private void validateNotDuplicateNickname(final String nickname) {
        if (customerDao.existCustomerByNickname(nickname)) {
            throw new CustomerDuplicatedDataException("이미 존재하는 닉네임입니다.");
        }
    }

    public CustomerLoginResponse login(final CustomerLoginRequest customerLoginRequest) {
        Customer customer = customerDao.findByUserId(customerLoginRequest.getUserId());
        customer.comparePasswordFrom(customerLoginRequest.getPassword());
        String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return CustomerLoginResponse.of(token, customer);
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
        customer.comparePasswordFrom(customerUpdateProfileRequest.getPassword());
        customerDao.update(customer.getId(), customerUpdateProfileRequest.getNickname());
    }

    @Transactional
    public void updatePassword(final TokenRequest tokenRequest,
                               final CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        Customer customer = customerDao.findById(tokenRequest.getId());
        customer.comparePasswordFrom(customerUpdatePasswordRequest.getOldPassword());
        customerDao.updatePassword(customer.getId(), customerUpdatePasswordRequest.getNewPassword());
    }

    public void withdraw(final TokenRequest tokenRequest, final CustomerPasswordRequest customerPasswordRequest) {
        Customer customer = customerDao.findById(tokenRequest.getId());
        customer.comparePasswordFrom(customerPasswordRequest.getPassword());
        customerDao.delete(customer.getId());
    }

    public void checkDuplicateUserId(final String userId) {
        validateNotDuplicateUserId(userId);
    }

    public void checkDuplicateNickname(final String nickname) {
        validateNotDuplicateNickname(nickname);
    }

    public void matchCustomerPassword(final TokenRequest tokenRequest, final CustomerPasswordRequest request) {
        Customer customer = customerDao.findById(tokenRequest.getId());
        customer.comparePasswordFrom(request.getPassword());
    }
}
