package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.Encryption;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.application.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.application.dto.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.application.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.customer.BasicPassword;

@Transactional(readOnly = true)
@Service
public class CustomerService {

    private final Encryption encryption;
    private final CustomerDao customerDao;

    public CustomerService(Encryption encryption, CustomerDao customerDao) {
        this.encryption = encryption;
        this.customerDao = customerDao;
    }

    @Transactional
    public Long save(CustomerSaveRequest request) {
        validateCustomerRequest(request);

        Customer customer = request.toEntity(encoderPassword(request.getPassword()));
        return customerDao.save(customer);
    }

    private String encoderPassword(String password) {
        return encryption.encrypt(new BasicPassword(password));
    }

    private void validateCustomerRequest(CustomerSaveRequest request) {
        if (customerDao.existByEmail(request.getEmail())) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 이메일입니다.");
        }

        if (customerDao.existByNickname(request.getNickname())) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 닉네임입니다.");
        }
    }

    public CustomerResponse findById(Long id) {
        Customer customer = customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] ID가 존재하지 않습니다."));
        return new CustomerResponse(customer);
    }

    @Transactional
    public void update(Long id, CustomerUpdateRequest request) {
        Customer customer = customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] ID가 존재하지 않습니다."));
        validateUpdateNickname(id, request, customer);

        customerDao.update(customer.updateCustomer(request.getNickname()));
    }

    private void validateUpdateNickname(Long id, CustomerUpdateRequest customerUpdateRequest, Customer customer) {
        boolean existNickname = customerDao.existByNicknameExcludedId(id, customerUpdateRequest.getNickname());
        if (existNickname) {
            throw new IllegalArgumentException("[ERROR] 중복되는 닉네임입니다.");
        }
    }

    @Transactional
    public void updatePassword(Long id, CustomerUpdatePasswordRequest request) {
        Customer customer = customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] ID가 존재하지 않습니다."));

        CustomerUpdatePasswordRequest encoderRequest = encoderPassword(request);
        validateUpdatePassword(encoderRequest, customer);

        customerDao.update(customer.updatePassword(encoderRequest.getNewPassword()));
    }

    private CustomerUpdatePasswordRequest encoderPassword(CustomerUpdatePasswordRequest request) {
        return new CustomerUpdatePasswordRequest(
                encryption.encrypt(request.getPrevPassword()),
                encryption.encrypt(new BasicPassword(request.getNewPassword())));
    }

    private void validateUpdatePassword(CustomerUpdatePasswordRequest request, Customer customer) {
        customer.equalPrevPassword(request.getPrevPassword());
        customer.nonEqualNewPassword(request.getNewPassword());
    }

    @Transactional
    public void delete(Long id) {
        customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] ID가 존재하지 않습니다."));
        customerDao.delete(id);
    }
}
