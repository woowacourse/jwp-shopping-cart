package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.application.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.application.dto.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.application.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long save(CustomerSaveRequest request) {
        validateCustomerRequest(request);
        return customerDao.save(request.toEntity());
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

    public void update(Long id, CustomerUpdateRequest customerUpdateRequest) {
        Customer saveCustomer = customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] ID가 존재하지 않습니다."));
        validateUpdateNickname(id, customerUpdateRequest, saveCustomer);

        customerDao.update(new Customer(
                id,
                saveCustomer.getEmail(),
                saveCustomer.getPassword(),
                customerUpdateRequest.getNickname())
        );
    }

    private void validateUpdateNickname(Long id, CustomerUpdateRequest customerUpdateRequest, Customer customer) {
        customer.validateNickname(customerUpdateRequest.getNickname());
        boolean existNickname = customerDao.existByNicknameExcludedId(id, customerUpdateRequest.getNickname());
        if (existNickname) {
            throw new IllegalArgumentException("[ERROR] 중복되는 닉네임입니다.");
        }
    }

    public void updatePassword(Long id, CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        Customer saveCustomer = customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] ID가 존재하지 않습니다."));
        validatePasswordUpdateNickname(customerUpdatePasswordRequest, saveCustomer);

        customerDao.update(new Customer(
                id,
                saveCustomer.getEmail(),
                customerUpdatePasswordRequest.getNewPassword(),
                saveCustomer.getNickname())
        );
    }

    private void validatePasswordUpdateNickname(CustomerUpdatePasswordRequest customerUpdatePasswordRequest, Customer customer) {
        customer.equalPrevPassword(customerUpdatePasswordRequest.getPrevPassword());
        customer.nonEqualNewPassword(customerUpdatePasswordRequest.getNewPassword());
        customer.validatePassword(customerUpdatePasswordRequest.getNewPassword());
    }

    public void delete(Long id) {
        customerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] ID가 존재하지 않습니다."));
        customerDao.delete(id);
    }
}
