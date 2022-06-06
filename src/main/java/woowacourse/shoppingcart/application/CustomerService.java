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
import woowacourse.auth.exception.BadRequestException;
import woowacourse.auth.exception.NotFoundException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
import woowacourse.shoppingcart.application.dto.SignInDto;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Password;
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
            throw new BadRequestException("이미 가입한 사용자입니다.");
        }
    }

    public TokenResponse signIn(final SignInDto signInDto) {
        final Email email = new Email(signInDto.getEmail());
        final Long customerId = checkSignUpCustomer(email);
        checkSignUpPassword(email, signInDto.getPassword());
        String payload = createPayload(new PermissionCustomerRequest(email.getValue()));
        return new TokenResponse(customerId, provider.createToken(payload));
    }

    private void checkSignUpPassword(Email email, String targetPassword) {
        try {
            String foundPassword = customerDao.findPasswordByEmail(email);
            verifyPassword(targetPassword, foundPassword);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("가입하지 않은 유저입니다.");
        }
    }

    private void verifyPassword(final String password, final String hashedPassword) {
        final Password newPassword = new Password(password);
        if (!newPassword.isSamePassword(hashedPassword)) {
            throw new BadRequestException("올바르지 않은 비밀번호입니다.");
        }
    }

    private Long checkSignUpCustomer(Email email) {
        try {
            return customerDao.findIdByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("가입하지 않은 유저입니다.");
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

    public CustomerResponse findCustomerById(Long customerId) {
        try {
            return customerDao.findByCustomerId(customerId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("회원을 조회할 수 없습니다.");
        }
    }

    public void updateCustomer(final Long customerId, final ModifiedCustomerDto modifiedCustomerDto) {
        final Customer modifiedCustomer = ModifiedCustomerDto.toModifiedCustomerDto(modifiedCustomerDto);
        final int affectedRows = customerDao.updateCustomer(customerId, modifiedCustomer);
        if (affectedRows != 1) {
            throw new BadRequestException("업데이트가 되지 않았습니다.");
        }
    }

    public void deleteCustomer(final Long customerId) {
        final int affectedRows = customerDao.deleteCustomer(customerId);
        if (affectedRows != 1) {
            throw new BadRequestException("삭제가 되지 않았습니다.");
        }
    }
}
