package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.request.PasswordRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Encryption.EncryptionStrategy;
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
    private final EncryptionStrategy encryptionStrategy;

    public CustomerService(final CustomerDao customerDao, final EncryptionStrategy encryptionStrategy) {
        this.customerDao = customerDao;
        this.encryptionStrategy = encryptionStrategy;
    }

    @Transactional
    public Long signUp(final SignUpRequest signUpRequest) {
        Customer customer = signUpRequest.toEntity();
        validateCustomer(customer);

        return customerDao.save(encrypt(customer));
    }

    public CustomerResponse login(final LoginRequest loginRequest) {
        Customer customer = findCustomerByUserId(loginRequest.getUserId());
        customer.validateMatchingLoginPassword(encrypt(loginRequest.getPassword()));

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
        customer.validateMatchingLoginPassword(encrypt(customerUpdateRequest.getPassword()));
        Customer customerForUpdate = createCustomerForUpdate(customerUpdateRequest, customer);

        customerDao.updateNickname(customerForUpdate.getId(), customerForUpdate.getNickname());
    }

    @Transactional
    public void updatePassword(final CustomerIdentificationRequest customerIdentificationRequest, final CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        Customer customer = findCustomerById(customerIdentificationRequest.getId());
        customer.validateMatchingOriginalPassword(encrypt(customerUpdatePasswordRequest.getOldPassword()));
        Customer customerForUpdate = createCustomerForUpdatePassword(customerUpdatePasswordRequest, customer);
        customerDao.updatePassword(customerForUpdate.getId(), customerForUpdate.getPassword());
    }

    @Transactional
    public void withdraw(final CustomerIdentificationRequest customerIdentificationRequest, final PasswordRequest passwordRequest) {
        Customer customer = findCustomerById(customerIdentificationRequest.getId());
        customer.validateMatchingLoginPassword(encrypt(passwordRequest.getPassword()));

        customerDao.delete(customer.getId());
    }

    public void validateDuplicateUserId(final String userId) {
        if (customerDao.findByUserId(userId).isPresent()) {
            throw new CustomerDuplicatedDataException("이미 존재하는 아이디입니다.");
        }
    }

    public void validateDuplicateNickname(final String nickname) {
        if (customerDao.findByNickname(nickname).isPresent()) {
            throw new CustomerDuplicatedDataException("이미 존재하는 닉네임입니다.");
        }
    }

    public void matchPassword(final CustomerIdentificationRequest customerIdentificationRequest,
                                      final PasswordRequest passwordRequest) {
        Customer customer = findCustomerById(customerIdentificationRequest.getId());
        customer.validateMatchingLoginPassword(encrypt(passwordRequest.getPassword()));
    }

    private Customer findCustomerByUserId(final String userId) {
        return customerDao.findByUserId(userId)
                .orElseThrow(() -> new LoginDataNotFoundException("존재하지 않는 회원입니다."));
    }

    private Customer findCustomerById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(() -> new CustomerDataNotFoundException("존재하지 않는 회원입니다."));
    }

    private Customer encrypt(final Customer customer) {
        return encryptionStrategy.encrypt(customer);
    }

    private String encrypt(final String text) {
        return encryptionStrategy.encrypt(text);
    }

    private void validateCustomer(final Customer customer) {
        validateDuplicateUserId(customer.getUserId());
        validateDuplicateNickname(customer.getNickname());
    }

    private Customer createCustomerForUpdate(final CustomerUpdateRequest customerUpdateRequest, final Customer customer) {
        return Customer.getEncrypted(customer.getId(), customer.getUserId(),
                customerUpdateRequest.getNickname(), encrypt(customer.getPassword()));
    }

    private Customer createCustomerForUpdatePassword(final CustomerUpdatePasswordRequest customerUpdatePasswordRequest, final Customer customer) {
        return Customer.from(customer.getId(), customer.getUserId(),
                customer.getNickname(), customerUpdatePasswordRequest.getNewPassword());
    }
}
