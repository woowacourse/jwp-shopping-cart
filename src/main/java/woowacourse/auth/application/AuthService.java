package woowacourse.auth.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional(readOnly = true)
    public boolean isExistEmail(String email) {
        return customerDao.existByEmail(email);
    }

    public CustomerResponse save(CustomerRequest customerRequest) {
        validateDuplicationEmail(customerRequest);
        Customer customer = customerRequest.createCustomer();
        customerDao.save(customer);
        return CustomerResponse.from(customer);
    }

    private void validateDuplicationEmail(CustomerRequest customerRequest) {
        if (isExistEmail(customerRequest.getEmail())) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }
    }

    @Transactional(readOnly = true)
    public Long loginCustomer(TokenRequest tokenRequest) {
        String password = Password.from(tokenRequest.getPassword()).getPassword();
        Optional<Long> idByEmailAndPassword =
            customerDao.findIdByEmailAndPassword(tokenRequest.getEmail(), password);

        return idByEmailAndPassword.orElseThrow(
                () -> new InvalidCustomerException("Email 또는 Password가 일치하지 않습니다."));
    }

    public TokenResponse createToken(Long customerId) {
        String accessToken = jwtTokenProvider.createToken(String.valueOf(customerId));
        return new TokenResponse(accessToken);
    }

    @Transactional(readOnly = true)
    public CustomerResponse find(Long customerId) {
        checkExistById(customerId);
        Customer customer = customerDao.findById(customerId);
        return CustomerResponse.from(customer);
    }

    private void checkExistById(Long customerId) {
        if (!customerDao.existById(customerId)) {
            throw new InvalidCustomerException();
        }
    }

    public void update(Long customerId, CustomerRequest customerRequest) {
        checkExistById(customerId);
        Customer customer = customerRequest.createCustomer(customerId);
        customerDao.update(customer);
    }

    public void delete(Long customerId) {
        checkExistById(customerId);
        customerDao.deleteById(customerId);
    }
}
