package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.SignInDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.dto.TokenResponseDto;
import woowacourse.shoppingcart.exception.AuthorizationFailException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomerService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Long signUp(final SignUpDto signUpDto){
        final Customer newCustomer = Customer.createWithoutId(
                signUpDto.getEmail(),
                signUpDto.getPassword(),
                signUpDto.getUsername()
        );

        return customerDao.save(newCustomer);
    }

    public Customer findCustomerById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    public TokenResponseDto login(final SignInDto signInDto) {
        final Customer customer = customerDao.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new AuthorizationFailException("로그인에 실패했습니다."));

        checkPassword(signInDto, customer);

        final String accessToken = makeAccessToken(customer);
        return new TokenResponseDto(accessToken, jwtTokenProvider.getValidityInMilliseconds());
    }

    private String makeAccessToken(final Customer customer) {
        return jwtTokenProvider.createToken(customer.getUsername());
    }

    private void checkPassword(final SignInDto signInDto, final Customer customer) {
        if (!customer.getPassword().equals(signInDto.getPassword())) {
            throw new AuthorizationFailException("로그인에 실패했습니다.");
        }
    }
}
