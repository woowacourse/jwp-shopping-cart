package woowacourse.shoppingcart.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.common.exception.BadRequestException;
import woowacourse.common.exception.NotFoundException;
import woowacourse.common.exception.UnauthorizedException;
import woowacourse.common.utils.EncryptAlgorithm;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.PhoneNumberResponse;
import woowacourse.shoppingcart.dto.SigninRequest;
import woowacourse.shoppingcart.entity.CustomerEntity;

@Service
public class CustomerService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public CustomerService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public void create(CustomerRequest customerRequest) {
        if (customerDao.existsByAccount(customerRequest.getAccount())) {
            throw new BadRequestException("이미 존재하는 계정입니다.");
        }
        Customer customer = customerRequest.toCustomer();
        customerDao.save(CustomerEntity.from(customer));
    }

    public TokenResponse signin(SigninRequest signinRequest) {
        String account = signinRequest.getAccount();
        CustomerEntity customerEntity = customerDao.findByAccount(account);

        if (!EncryptAlgorithm.match(signinRequest.getPassword(), customerEntity.getPassword())) {
            throw new UnauthorizedException("로그인이 불가능합니다.");
        }

        return new TokenResponse(
                jwtTokenProvider.createToken(String.valueOf(customerEntity.getId())));
    }

    public CustomerResponse findById(Long customerId) {
        Optional<CustomerEntity> customerEntity = customerDao.findById(customerId);
        return customerEntity
                .map(it -> new CustomerResponse(
                        it.getAccount(),
                        it.getNickname(),
                        it.getAddress(),
                        PhoneNumberResponse.from(it.getPhoneNumber())))
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용지입니다"));
    }
}
