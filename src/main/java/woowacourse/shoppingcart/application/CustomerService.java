package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.global.exception.InvalidCustomerException;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.application.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.application.dto.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.application.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Transactional(readOnly = true)
@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Long save(final CustomerSaveRequest request) {
        validateCustomerRequest(request);
        return customerDao.save(request.toEntity());
    }

    private void validateCustomerRequest(final CustomerSaveRequest request) {
        if (customerDao.existByEmail(request.getEmail())) {
            throw new InvalidCustomerException("[ERROR] 이미 존재하는 이메일입니다.");
        }

        if (customerDao.existByNickname(request.getNickname())) {
            throw new InvalidCustomerException("[ERROR] 이미 존재하는 닉네임입니다.");
        }
    }

    public CustomerResponse findById(final Long id) {
        Customer customer = customerDao.findById(id)
                .orElseThrow(() -> new InvalidCustomerException("[ERROR] ID가 존재하지 않습니다."));
        return new CustomerResponse(customer);
    }

    @Transactional
    public void update(final Long id, final CustomerUpdateRequest customerUpdateRequest) {
        Customer saveCustomer = customerDao.findById(id)
                .orElseThrow(() -> new InvalidCustomerException("[ERROR] ID가 존재하지 않습니다."));
        validateUpdateNickname(id, customerUpdateRequest, saveCustomer);

        customerDao.update(new Customer(
                id,
                saveCustomer.getEmail(),
                saveCustomer.getPassword(),
                customerUpdateRequest.getNickname())
        );
    }

    private void validateUpdateNickname(final Long id, final CustomerUpdateRequest customerUpdateRequest, final Customer customer) {
        customer.validateNickname(customerUpdateRequest.getNickname());
        boolean existNickname = customerDao.existByNicknameExcludedId(id, customerUpdateRequest.getNickname());
        if (existNickname) {
            throw new InvalidCustomerException("[ERROR] 중복되는 닉네임입니다.");
        }
    }

    @Transactional
    public void updatePassword(final Long id, final CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        Customer saveCustomer = customerDao.findById(id)
                .orElseThrow(() -> new InvalidCustomerException("[ERROR] ID가 존재하지 않습니다."));
        validatePasswordUpdateNickname(customerUpdatePasswordRequest, saveCustomer);

        customerDao.update(new Customer(
                id,
                saveCustomer.getEmail(),
                customerUpdatePasswordRequest.getNewPassword(),
                saveCustomer.getNickname())
        );
    }

    private void validatePasswordUpdateNickname(final CustomerUpdatePasswordRequest customerUpdatePasswordRequest, final Customer customer) {
        customer.equalPrevPassword(customerUpdatePasswordRequest.getPrevPassword());
        customer.nonEqualNewPassword(customerUpdatePasswordRequest.getNewPassword());
        customer.validatePassword(customerUpdatePasswordRequest.getNewPassword());
    }

    @Transactional
    public void delete(final Long id) {
        customerDao.findById(id)
                .orElseThrow(() -> new InvalidCustomerException("[ERROR] ID가 존재하지 않습니다."));
        customerDao.delete(id);
    }
}
