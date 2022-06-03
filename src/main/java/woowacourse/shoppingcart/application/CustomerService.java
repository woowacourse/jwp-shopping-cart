package woowacourse.shoppingcart.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.common.exception.BadRequestException;
import woowacourse.common.exception.NotFoundException;
import woowacourse.common.exception.UnauthorizedException;
import woowacourse.common.utils.EncryptAlgorithm;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.vo.Address;
import woowacourse.shoppingcart.domain.customer.vo.EncryptPassword;
import woowacourse.shoppingcart.domain.customer.vo.Nickname;
import woowacourse.shoppingcart.domain.customer.vo.Password;
import woowacourse.shoppingcart.domain.customer.vo.PhoneNumber;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.PhoneNumberResponse;
import woowacourse.shoppingcart.dto.SigninRequest;
import woowacourse.shoppingcart.dto.TokenResponse;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.repository.CustomerRepository;

@Service
public class CustomerService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;
    private final CustomerRepository customerRepository;

    public CustomerService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao,
            CustomerRepository customerRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
        this.customerRepository = customerRepository;
    }

    public void create(CustomerRequest customerRequest) {
        if (customerDao.existsByAccount(customerRequest.getAccount())) {
            throw new BadRequestException("이미 존재하는 계정입니다.");
        }
        customerRepository.save(toCustomer(customerRequest));
    }

    private Customer toCustomer(CustomerRequest request) {
        return new Customer(
                request.getAccount(),
                request.getNickname(),
                new EncryptPassword(new Password(request.getPassword())),
                request.getAddress(),
                request.getPhoneNumber().toPhoneNumber());
    }

    public TokenResponse signin(SigninRequest signinRequest) {
        String account = signinRequest.getAccount();
        Customer customer = getCustomer(account);
        validatePassword(signinRequest, customer);
        String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(token);
    }

    private void validatePassword(SigninRequest signinRequest, Customer customer) {
        if (checkPassword(signinRequest.getPassword(), customer.getPassword().getValue())) {
            throw new UnauthorizedException("로그인이 불가능합니다.");
        }
    }

    private Customer getCustomer(String account) {
        return customerRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }

    private boolean checkPassword(String rawPassword, String encodedPassword) {
        return !EncryptAlgorithm.match(rawPassword, encodedPassword);
    }

    public CustomerResponse findById(Long customerId) {
        Optional<CustomerEntity> customerEntity = customerDao.findById(customerId);
        return customerEntity
                .map(it -> new CustomerResponse(
                        it.getAccount(),
                        it.getNickname(),
                        it.getAddress(),
                        PhoneNumberResponse.from(it.getPhoneNumber())))
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }

    public void delete(Long customerId, PasswordRequest passwordRequest) {
        Optional<CustomerEntity> customerEntity = customerDao.findById(customerId);
        if (customerEntity.isEmpty()) {
            throw new NotFoundException("존재하지 않는 사용자입니다.");
        }

        String rawPassword = passwordRequest.getPassword();
        String encryptPassword = customerEntity.get().getPassword();
        if (!EncryptAlgorithm.match(rawPassword, encryptPassword)) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        customerDao.deleteById(customerId);
    }

    public void update(Long customerId, CustomerUpdateRequest customerUpdateRequest) {
        if (!customerDao.existsById(customerId)) {
            throw new NotFoundException("존재하지 않는 사용자입니다.");
        }

        Nickname nickname = new Nickname(customerUpdateRequest.getNickname());
        Address address = new Address(customerUpdateRequest.getAddress());
        PhoneNumber phoneNumber = customerUpdateRequest.getPhoneNumber().toPhoneNumber();

        CustomerEntity updateEntity = new CustomerEntity(customerId, null, nickname.getValue(),
                null, address.getValue(), phoneNumber.getValue());

        customerDao.update(updateEntity);
    }
}
