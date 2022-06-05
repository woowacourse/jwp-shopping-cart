package woowacourse.shoppingcart.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.PermissionCustomerRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
import woowacourse.shoppingcart.application.dto.SignInDto;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.NewPassword;
import woowacourse.shoppingcart.dto.CustomerResponse;

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
        final Long customerId = checkSignUpCustomer(email);
        final String foundPassword = customerDao.findPasswordByEmail(email);
        verifyPassword(signInDto.getPassword(), foundPassword);
        String payload = createPayload(new PermissionCustomerRequest(email.getValue()));
        return new TokenResponse(customerId, provider.createToken(payload));
    }

    private void verifyPassword(final String password, final String hashedPassword) {
        final NewPassword newPassword = new NewPassword(password);
        if (!newPassword.isSamePassword(hashedPassword)) {
            throw new IllegalArgumentException("올바르지 않은 비밀번호입니다.");
        }
    }

    private Long checkSignUpCustomer(Email email) {
        try {
            return customerDao.findIdByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("가입하지 않은 유저입니다.");
        }
    }

    private String createPayload(final PermissionCustomerRequest email) {
        try {
            ObjectMapper mapper = new JsonMapper();
            return mapper.writeValueAsString(email);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    public void updateCustomer(final Long customerId, final ModifiedCustomerDto modifiedCustomerDto) {
        final Customer modifiedCustomer = ModifiedCustomerDto.toModifiedCustomerDto(modifiedCustomerDto);
        final int affectedRows = customerDao.updateCustomer(customerId,modifiedCustomer);
        if (affectedRows != 1) {
            throw new IllegalArgumentException("업데이트가 되지 않았습니다.");
        }
    }

    public CustomerResponse findCustomerByEmail(PermissionCustomerRequest email) {
        return customerDao.findByUserEmail(new Email(email.getEmail()));
    }

    public void deleteCustomer(final Long customerId) {
        final int affectedRows = customerDao.deleteCustomer(customerId);
        if (affectedRows != 1) {
            throw new IllegalArgumentException("삭제가 되지 않았습니다.");
        }
    }

    public CustomerResponse findCustomerById(Long customerId) {
        try {
            return customerDao.findByCustomerId(customerId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("회원을 조회할 수 없습니다.");
        }

    }
}
