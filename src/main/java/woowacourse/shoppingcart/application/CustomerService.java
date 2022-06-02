package woowacourse.shoppingcart.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
import woowacourse.shoppingcart.application.dto.SignInDto;
import woowacourse.shoppingcart.application.dto.AddressResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.NewPassword;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider provider;

    public CustomerService(CustomerDao customerDao, JwtTokenProvider provider) {
        this.customerDao = customerDao;
        this.provider = provider;
    }

    public Long createCustomer(final CustomerDto newCustomer) {
        final Customer customer = CustomerDto.toCustomer(newCustomer);
        try {
            return customerDao.createCustomer(customer);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("이미 가입한 사용자입니다.");
        }
    }

    public TokenResponse signIn(final SignInDto signInDto) {
        final Email email = new Email(signInDto.getEmail());
        final NewPassword password = new NewPassword(signInDto.getPassword());
        final String foundPassword = customerDao.findPasswordByEmail(email);
        verifyPassword(password, foundPassword);
        AddressResponse tokenPayloadDto = customerDao.findByUserEmail(email);
        String payload = createPayload(tokenPayloadDto);
        return new TokenResponse(tokenPayloadDto.getId(), provider.createToken(payload));
    }

    private void verifyPassword(final NewPassword password, final String hashedPassword) {
        if (!password.isSamePassword(hashedPassword)) {
            throw new IllegalArgumentException("올바르지 않은 비밀번호입니다.");
        }
    }

    private String createPayload(AddressResponse tokenPayloadDto) {
        try {
            ObjectMapper mapper = new JsonMapper();
            return mapper.writeValueAsString(tokenPayloadDto);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    public void updateCustomer(ModifiedCustomerDto modifiedCustomerDto) {
        final Customer modifiedCustomer = ModifiedCustomerDto.toModifiedCustomerDto(modifiedCustomerDto);
        final int affectedRows = customerDao.updateCustomer(modifiedCustomer);
        if (affectedRows != 1) {
            throw new IllegalArgumentException("업데이트가 되지 않았습니다.");
        }
    }
}
