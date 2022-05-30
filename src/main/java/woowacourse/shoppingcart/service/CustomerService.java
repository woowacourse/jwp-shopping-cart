package woowacourse.shoppingcart.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignInDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.dto.TokenResponseDto;
import woowacourse.shoppingcart.exception.AuthorizationFailException;
import woowacourse.shoppingcart.exception.DuplicateNameException;
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

    public CustomerDto findCustomerById(final Long id) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
        return new CustomerDto(customer.getId(), customer.getEmail(), customer.getUsername());
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

    public CustomerDto updateCustomer(final CustomerDto customerDto){
        final Customer updateCustomer = Customer.createWithoutPassword(
                customerDto.getId(),
                customerDto.getEmail(),
                customerDto.getUsername());
        try{
            customerDao.update(updateCustomer);
            return findCustomerById(customerDto.getId());
        }catch (DataIntegrityViolationException e){
            throw new DuplicateNameException("수정하려는 이름이 이미 존재합니다.");
        }
    }
}
