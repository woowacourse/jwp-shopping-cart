package woowacourse.auth.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public CustomerResponse save(CustomerRequest customerRequest) {
        validateDuplicationEmail(customerRequest);
        Customer customer = customerRequest.createCustomer();
        customerDao.save(customer);
        return CustomerResponse.from(customer);
    }

    private void validateDuplicationEmail(CustomerRequest customerRequest) {
        if (customerDao.existEmail(customerRequest.getEmail())) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }
    }

    public Long loginCustomer(TokenRequest tokenRequest) {
        Optional<Long> idByEmailAndPassword = customerDao.findIdByEmailAndPassword(tokenRequest.getEmail(),
                tokenRequest.getPassword());
        return idByEmailAndPassword.orElseThrow(
                () -> new InvalidCustomerException("Email 또는 Password가 일치하지 않습니다."));
    }

    public TokenResponse createToken(Long customerId) {
        String accessToken = jwtTokenProvider.createToken(String.valueOf(customerId));
        return new TokenResponse(accessToken);
    }

    public CustomerResponse findById(Long customerId) {
        checkExistId(customerId);
        Customer customer = customerDao.findCustomerById(customerId);
        return CustomerResponse.from(customer);
    }

    private void checkExistId(Long customerId) {
        if (!customerDao.existId(customerId)) {
            throw new InvalidCustomerException();
        }
    }

    public void update(Long customerId, CustomerRequest customerRequest) {
        checkExistId(customerId);
        Customer customer = customerRequest.createCustomer(customerId);
        customerDao.update(customer);
    }
}
