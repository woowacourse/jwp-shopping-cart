package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.*;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.UserNameDuplicationRequest;
import woowacourse.shoppingcart.dto.UserNameDuplicationResponse;
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
        validateUserName(customerRequest);
        RawPassword rawPassword = new RawPassword(customerRequest.getPassword());
        Customer customer = Customer.of(
                customerRequest.getUserName(), passwordEncoder.encode(rawPassword),
                customerRequest.getNickName(), customerRequest.getAge()
        );
        customerDao.save(customer);
    }

    private void validateUserName(CustomerRequest customerRequest) {
        if (isDuplicateUserName(customerRequest.getUserName())) {
            throw new InvalidArgumentRequestException("기존 회원 아이디와 중복되는 아이디입니다.");
        }
    }

    public UserNameDuplicationResponse checkDuplication(UserNameDuplicationRequest userNameDuplicationRequest) {
        boolean isUnique = !isDuplicateUserName(userNameDuplicationRequest.getUsername());
        return new UserNameDuplicationResponse(isUnique);
    }

    private boolean isDuplicateUserName(String userName) {
        UserNames userNames = UserNames.from(customerDao.findAllUserNames());
        return userNames.contains(userName);
    }

    public void updatePassword(Customer customer, PasswordRequest passwordRequest) {
        EncodePassword encodePassword = passwordEncoder.encode(new RawPassword(passwordRequest.getNewPassword()));
        Customer updateCustomer = customer.updatePassword(encodePassword);
        customerDao.updatePassword(updateCustomer);
    }

    public void updateInfo(Customer customer, CustomerRequest customerRequest) {
        Customer updateCustomer = customer.updateInfo(customerRequest.getNickName(), customerRequest.getAge());
        customerDao.updateInfo(updateCustomer);
    }

    public void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }
}
