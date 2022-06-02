package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.ChangeCustomerRequest;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Long createCustomer(CustomerCreateRequest customerCreateRequest) {
        validateDuplicateNickname(customerCreateRequest.getNickname());
        validateDuplicateEmail(customerCreateRequest.getEmail());
        return customerDao.save(customerCreateRequest.toCustomer());
    }

    public CustomerResponse findCustomerByEmail(String email) {
        Customer customer = customerDao.findIdByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return CustomerResponse.from(customer);
    }

    @Transactional
    public void deleteCustomer(String email) {
        customerDao.deleteByEmail(email);
    }

    @Transactional
    public void changePassword(String email, ChangePasswordRequest changePasswordRequest) {
        Customer customer = customerDao.findIdByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        customer.changePassword(changePasswordRequest.getPrevPassword(), changePasswordRequest.getNewPassword());
        customerDao.updatePassword(customer);
    }

    @Transactional
    public void changeNickname(String email, ChangeCustomerRequest changeCustomerRequest) {
        Customer customer = customerDao.findIdByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        String changeNickname = changeCustomerRequest.getNickname();
        validateDuplicateNickname(changeNickname);
        customer.changeNickname(changeNickname);
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
}
