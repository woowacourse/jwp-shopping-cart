package woowacourse.shoppingcart.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.TokenPayloadDto;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.NewPassword;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider provider;

    public CustomerService(CustomerDao customerDao, JwtTokenProvider provider) {
        this.customerDao = customerDao;
        this.provider = provider;
    }

    public Long createCustomer(final CustomerDto newCustomer) {
        final Customer customer = CustomerDto.toCustomer(newCustomer);
        return customerDao.createCustomer(customer);
    }

    public TokenResponse signIn(final TokenRequest tokenRequest) {
        final Email email = new Email(tokenRequest.getEmail());
        final NewPassword password = new NewPassword(tokenRequest.getPassword());
        final String foundPassword = customerDao.findPasswordByEmail(email);
        verifyPassword(password, foundPassword);
        TokenPayloadDto tokenPayloadDto = customerDao.findByUserEmail(email);
        String payload = createPayload(tokenPayloadDto);
        return new TokenResponse(provider.createToken(payload));
    }

    private void verifyPassword(final NewPassword password, final String hashedPassword) {
        if (!password.isSamePassword(hashedPassword)) {
            throw new IllegalArgumentException("올바르지 않은 비밀번호입니다.");
        }
    }

    private String createPayload(TokenPayloadDto tokenPayloadDto) {
        try {
            ObjectMapper mapper = new JsonMapper();
            return mapper.writeValueAsString(tokenPayloadDto);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }

    }
}
