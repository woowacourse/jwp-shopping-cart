package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.application.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerIdentificationRequest;
import woowacourse.shoppingcart.application.dto.response.CustomerResponse;
import woowacourse.shoppingcart.application.dto.request.LoginRequest;
import woowacourse.shoppingcart.application.dto.request.SignUpRequest;
import woowacourse.shoppingcart.exception.datanotfound.CustomerDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotfound.LoginDataNotFoundException;
import woowacourse.shoppingcart.exception.duplicateddata.CustomerDuplicatedDataException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Long signUp(final SignUpRequest signUpRequest) {
        Customer customer = signUpRequest.toEntity();
        validateCustomer(customer);
        return customerDao.save(customer);
    }

    public CustomerResponse login(final LoginRequest loginRequest) {
        validateExistingCustomer(loginRequest.getUserId(), loginRequest.getPassword());
        Customer customer = findCustomerByUserId(loginRequest.getUserId());
        return CustomerResponse.from(customer);
    }

    public CustomerResponse findByCustomerId(final CustomerIdentificationRequest customerIdentificationRequest) {
        Customer customer = findCustomerById(customerIdentificationRequest.getId());
        return CustomerResponse.from(customer);
    }

    @Transactional
    public void update(final CustomerIdentificationRequest customerIdentificationRequest, final CustomerUpdateRequest customerUpdateRequest) {
        validateDuplicateNickname(customerUpdateRequest.getNickname());
        Customer customer = findCustomerById(customerIdentificationRequest.getId());
        Customer customerForUpdate = createCustomerForUpdate(customerUpdateRequest, customer);
        customerDao.update(customerForUpdate.getId(), customerForUpdate.getNickname());
    }

    @Transactional
    public void updatePassword(final CustomerIdentificationRequest customerIdentificationRequest, final CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        Customer customer = findCustomerById(customerIdentificationRequest.getId());
        customer.validateMatchPassword(customerUpdatePasswordRequest.getOldPassword());
        Customer customerForUpdate = createCustomerForUpdatePassword(customerUpdatePasswordRequest, customer);
        customerDao.updatePassword(customerForUpdate.getId(), customerForUpdate.getPassword());
    }

    @Transactional
    public void withdraw(final CustomerIdentificationRequest customerIdentificationRequest) {
        Customer customer = findCustomerById(customerIdentificationRequest.getId());
        customerDao.delete(customer.getId());
    }

    private Customer findCustomerByUserId(final String userId) {
        return customerDao.findByUserId(userId)
                .orElseThrow(() -> new LoginDataNotFoundException("존재하지 않는 회원입니다."));
    }

    private Customer findCustomerById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(() -> new CustomerDataNotFoundException("존재하지 않는 회원입니다."));
    }

    private void validateCustomer(final Customer customer) {
        validateDuplicateUserId(customer.getUserId());
        validateDuplicateNickname(customer.getNickname());
    }

    private void validateDuplicateUserId(final String userId) {
        if (customerDao.existCustomerByUserId(userId)) {
            throw new CustomerDuplicatedDataException("이미 존재하는 아이디입니다.");
        }
    }

    private void validateDuplicateNickname(final String nickname) {
        if (customerDao.existCustomerByNickname(nickname)) {
            throw new CustomerDuplicatedDataException("이미 존재하는 닉네임입니다.");
        }
    }

    private void validateExistingCustomer(final String userId, final String password) {
        if (!customerDao.existCustomer(userId, password)) {
            throw new LoginDataNotFoundException("존재하지 않는 회원입니다.");
        }
    }

    private Customer createCustomerForUpdate(final CustomerUpdateRequest customerUpdateRequest, final Customer customer) {
        return new Customer(customer.getId(), customer.getUserId(),
                customerUpdateRequest.getNickname(), customer.getPassword());
    }

    private Customer createCustomerForUpdatePassword(final CustomerUpdatePasswordRequest customerUpdatePasswordRequest, final Customer customer) {
        return new Customer(customer.getId(), customer.getUserId(),
                customer.getNickname(), customerUpdatePasswordRequest.getNewPassword());
    }
}
