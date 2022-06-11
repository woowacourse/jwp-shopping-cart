package woowacourse.shoppingcart.application;

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
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.request.PasswordRequest;
import woowacourse.shoppingcart.dto.request.PhoneNumberRequest;
import woowacourse.shoppingcart.dto.response.PhoneNumberResponse;
import woowacourse.shoppingcart.dto.request.SignInRequest;
import woowacourse.shoppingcart.dto.response.TokenResponse;
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
                new PhoneNumber(toPhoneNumber(request.getPhoneNumber())));
    }

    private String toPhoneNumber(PhoneNumberRequest phoneNumber) {
        return phoneNumber.getStart() + phoneNumber.getMiddle() + phoneNumber.getLast();
    }

    public TokenResponse signIn(SignInRequest signinRequest) {
        String account = signinRequest.getAccount();
        Customer customer = getCustomerBy(account);
        validatePassword(signinRequest.getPassword(), customer.getPassword().getValue());
        String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(token);
    }

    private Customer getCustomerBy(String account) {
        return customerRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }

    private void validatePassword(String password, String encodedPassword) {
        if (checkPassword(password, encodedPassword)) {
            throw new UnauthorizedException("로그인이 불가능합니다.");
        }
    }

    private boolean checkPassword(String rawPassword, String encryptPassword) {
        return !EncryptAlgorithm.match(rawPassword, encryptPassword);
    }

    public CustomerResponse findById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(this::toCustomerResponse)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getAccount().getValue(),
                customer.getNickname().getValue(),
                customer.getAddress().getValue(),
                PhoneNumberResponse.from(customer.getPhoneNumber().getValue()));
    }

    public void update(Long customerId, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = getCustomerBy(customerId);
        Nickname nickname = new Nickname(customerUpdateRequest.getNickname());
        Address address = new Address(customerUpdateRequest.getAddress());
        PhoneNumber phoneNumber = new PhoneNumber(
                toPhoneNumber(customerUpdateRequest.getPhoneNumber()));
        customer.update(nickname, address, phoneNumber);
        customerRepository.update(customer);
    }

    public void delete(Long customerId, PasswordRequest passwordRequest) {
        Customer customer = getCustomerBy(customerId);
        if (checkPassword(passwordRequest.getPassword(), customer.getPassword().getValue())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
        customerDao.deleteById(customerId);
    }

    private Customer getCustomerBy(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }
}
