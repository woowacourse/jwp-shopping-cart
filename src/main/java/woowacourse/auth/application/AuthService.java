package woowacourse.auth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.EmailAuthentication;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.SignInDto;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.password.Password;
import woowacourse.shoppingcart.domain.customer.password.PasswordFactory;
import woowacourse.shoppingcart.domain.customer.password.PasswordType;
import woowacourse.shoppingcart.repository.CustomerRepository;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final JwtTokenProvider provider;

    public AuthService(final CustomerRepository customerRepository, final JwtTokenProvider provider) {
        this.customerRepository = customerRepository;
        this.provider = provider;
    }


    public TokenResponse signIn(final SignInDto signInDto) {
        final Email email = new Email(signInDto.getEmail());
        final Password password = PasswordFactory.of(PasswordType.HASHED, signInDto.getPassword());

        final Password foundPassword = customerRepository.findPasswordByEmail(email);
        verifyPassword(password, foundPassword);
        Customer tokenPayloadDto = customerRepository.findByUserEmail(email);
        String payload = createPayload(new EmailAuthentication(email.getValue()));
        return new TokenResponse(tokenPayloadDto.getId(), provider.createToken(payload));
    }

    private void verifyPassword(final Password hashedPassword, final Password existedPassword) {
        if (!hashedPassword.isSamePassword(existedPassword)) {
            throw new IllegalArgumentException("올바르지 않은 비밀번호입니다.");
        }
    }

    private String createPayload(final EmailAuthentication email) {
        try {
            ObjectMapper mapper = new JsonMapper();
            return mapper.writeValueAsString(email);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }
}
