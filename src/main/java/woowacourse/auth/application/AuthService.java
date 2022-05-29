package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Service
public class AuthService {

    private final CustomerDao customerDao;

    private JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public CustomerResponse register(CustomerRequest customerRequest) {
        final Customer customer = new Customer(customerRequest.getEmail(), customerRequest.getName(), customerRequest.getPhone(), customerRequest.getAddress(), customerRequest.getPassword());
        final Customer savedCustomer = customerDao.save(customer);
        return new CustomerResponse(savedCustomer.getId(), savedCustomer.getEmail(), savedCustomer.getName(), savedCustomer.getPhone(), savedCustomer.getAddress());
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        Customer customer = customerDao.findByEmail(tokenRequest.getEmail());
        if (!customer.checkPassword(tokenRequest.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        final String accessToken = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(accessToken);
    }

    public CustomerResponse findCustomerByToken(String token) {
        Long id = Long.parseLong(jwtTokenProvider.getPayload(token));
        Customer customer = customerDao.findById(id);
        return new CustomerResponse(customer.getId(), customer.getEmail(), customer.getName(), customer.getPhone(), customer.getAddress());
    }
}
