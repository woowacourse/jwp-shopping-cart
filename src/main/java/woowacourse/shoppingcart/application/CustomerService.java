package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.shoppingcart.application.dto.CustomerSaveRequest;
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
}
