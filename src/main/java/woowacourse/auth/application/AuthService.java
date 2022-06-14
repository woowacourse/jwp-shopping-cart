package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.PasswordDto;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.EmptyResultException;
import woowacourse.shoppingcart.exception.UserNotFoundException;

@Service
@Transactional
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerService customerService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    public TokenResponse createToken(LoginRequest loginRequest) {
        String accessToken = jwtTokenProvider.createToken(loginRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByUsername(String username) {
        try {
            return customerService.findByUsername(username);
        } catch (EmptyResultException exception) {
            throw new UserNotFoundException("해당하는 username이 없습니다.");
        }
    }

    public void validateLogin(LoginRequest loginRequest) {
        try {
            Customer customer = customerService.findByUsername(loginRequest.getUsername());
            customer.matchPassword(customerService.convertPassword(loginRequest.getPassword()));
        } catch (EmptyResultException exception) {
            throw new UserNotFoundException("해당하는 username이 없습니다.");
        }
    }

    public void matchPassword(String username, PasswordDto passwordDto) {
        Customer customer = customerService.findByUsername(username);
        customer.matchPassword(customerService.convertPassword(passwordDto.getPassword()));
    }
}
