package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.ChangeCustomerRequest;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CreateCustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long createCustomer(CreateCustomerRequest customerCreateRequest) {
        validateDuplication(customerCreateRequest);
        return customerDao.save(customerCreateRequest.toCustomer());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerByEmail(String email) {
        return CustomerResponse.from(getCustomerByEmail(email));
    }

    public void deleteCustomer(String email) {
        customerDao.deleteByEmail(email);
    }

    public void changePassword(String email, ChangePasswordRequest changePasswordRequest) {
        Customer customer = getCustomerByEmail(email);
        customer.changePassword(changePasswordRequest.getPrevPassword(), changePasswordRequest.getNewPassword());
        customerDao.updatePassword(customer);
    }

    public void changeNickname(String email, ChangeCustomerRequest changeCustomerRequest) {
        Customer customer = getCustomerByEmail(email);
        String nicknameToChange = changeCustomerRequest.getNickname();
        validateDuplicateNickname(nicknameToChange);
        customer.changeNickname(nicknameToChange);
        customerDao.updateNickname(customer);
    }

    private void validateDuplicateNickname(String nickname) {
        if (customerDao.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }

    private void validateDuplicateEmail(String email) {
        if (customerDao.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
    }

    public Customer getCustomerByEmail(String email) {
        return customerDao.findIdByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    private void validateDuplication(CreateCustomerRequest customerCreateRequest) {
        validateDuplicateNickname(customerCreateRequest.getNickname());
        validateDuplicateEmail(customerCreateRequest.getEmail());
    }
}
