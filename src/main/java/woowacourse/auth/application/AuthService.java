package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.utils.Encryptor;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidTokenException;
import woowacourse.shoppingcart.utils.CustomerInformationValidator;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public CustomerResponse register(CustomerRequest customerRequest) {
        CustomerInformationValidator.validatePassword(customerRequest.getPassword());
        final Customer customer = new Customer(customerRequest.getEmail(), customerRequest.getName(),
                customerRequest.getPhone(), customerRequest.getAddress(),
                Encryptor.encrypt(customerRequest.getPassword()));
        final Customer savedCustomer = customerDao.save(customer);
        return new CustomerResponse(savedCustomer.getId(), savedCustomer.getEmail(), savedCustomer.getName(),
                savedCustomer.getPhone(), savedCustomer.getAddress());
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        Customer customer = customerDao.findByEmail(tokenRequest.getEmail())
                .orElseThrow(() -> new InvalidCustomerException("존재하지 않는 이메일 입니다."));
        if (!customer.checkPassword(Encryptor.encrypt(tokenRequest.getPassword()))) {
            throw new InvalidCustomerException("비밀번호가 일치하지 않습니다.");
        }
        final String accessToken = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(accessToken);
    }

    public CustomerResponse findCustomerById(Long id) {
        final Customer customer = customerDao.findById(id);
        return new CustomerResponse(customer.getId(), customer.getEmail(), customer.getName(), customer.getPhone(),
                customer.getAddress());
    }

    public void edit(Long id, CustomerRequest customerRequest) {
        CustomerInformationValidator.validatePassword(customerRequest.getPassword());
        final Customer customer = new Customer(id, customerRequest.getEmail(), customerRequest.getName(),
                customerRequest.getPhone(), customerRequest.getAddress(),
                Encryptor.encrypt(customerRequest.getPassword()));
        customerDao.edit(customer);
    }

    public void delete(Long id) {
        customerDao.delete(id);
    }

    public boolean validateEmail(String email) {
        return customerDao.findByEmail(email).isPresent();
    }

    private void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }
}
