package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.SignInRequestDto;
import woowacourse.auth.dto.SignInResponseDto;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.exception.AuthorizationFailException;
import woowacourse.shoppingcart.exception.ForbiddenException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public AuthService(final JwtTokenProvider jwtTokenProvider,
                       final CustomerDao customerDao,
                       final PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }


    public String extractEmail(final String token) {
        return jwtTokenProvider.extractClaim(token);
    }

    public SignInResponseDto login(final SignInRequestDto signInDto) {
        final Customer customer = customerDao.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new AuthorizationFailException("로그인에 실패했습니다."));

        checkPassword(signInDto, customer);

        final CustomerDto customerDto = new CustomerDto(customer.getId(), customer.getEmail(), customer.getUsername());
        final String accessToken = createAccessToken(customerDto);
        return new SignInResponseDto(accessToken, jwtTokenProvider.getValidityInMilliseconds(), customerDto);
    }

    private String createAccessToken(final CustomerDto customer) {
        return jwtTokenProvider.createToken(customer.getEmail());
    }

    private void checkPassword(final SignInRequestDto signInDto, final Customer customer) {
        if (!passwordEncoder.matches(signInDto.getPassword(), customer.getPassword())) {
            throw new AuthorizationFailException("id 또는 비밀번호가 틀렸습니다.");
        }
    }

    public void checkAuthorization(final Long id, final String email) {
        final Customer customer = customerDao.findByEmail(email)
                .orElseThrow(InvalidCustomerException::new);
        if (!customer.getId().equals(id)) {
            throw new ForbiddenException();
        }
    }
}
