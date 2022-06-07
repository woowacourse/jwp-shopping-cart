package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.*;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.UsernameDuplicationRequest;
import woowacourse.shoppingcart.dto.UsernameDuplicationResponse;
import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

@Transactional
@Service
public class CustomerService {
    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerDao customerDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void addCustomer(CustomerRequest customerRequest) {
        validateusername(customerRequest);
        RawPassword rawPassword = new RawPassword(customerRequest.getPassword());
        Customer customer = Customer.of(
                customerRequest.getUsername(), passwordEncoder.encode(rawPassword),
                customerRequest.getNickname(), customerRequest.getAge()
        );
        customerDao.save(customer);
    }

    private void validateusername(CustomerRequest customerRequest) {
        if (isDuplicateUsername(customerRequest.getUsername())) {
            throw new InvalidArgumentRequestException("기존 회원 아이디와 중복되는 아이디입니다.");
        }
    }

    @Transactional(readOnly = true)
    public UsernameDuplicationResponse checkDuplication(UsernameDuplicationRequest usernameDuplicationRequest) {
        Username username = new Username(usernameDuplicationRequest.getUsername());
        boolean isUnique = !isDuplicateUsername(username.getUsername());
        return new UsernameDuplicationResponse(isUnique);
    }

    private boolean isDuplicateUsername(String username) {
        Usernames usernames = Usernames.from(customerDao.findAllUserNames());
        return usernames.contains(username);
    }

    public void updatePassword(Customer customer, PasswordRequest passwordRequest) {
        EncodePassword encodePassword = passwordEncoder.encode(new RawPassword(passwordRequest.getNewPassword()));
        Customer updateCustomer = customer.updatePassword(encodePassword);
        customerDao.updatePassword(updateCustomer.getPassword(), customer.getUsername());
    }

    public void updateInfo(Customer customer, CustomerRequest customerRequest) {
        customerDao.updateInfo(customer.getUsername(), customerRequest.getNickname(), customerRequest.getAge());
    }

    public void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }
}
