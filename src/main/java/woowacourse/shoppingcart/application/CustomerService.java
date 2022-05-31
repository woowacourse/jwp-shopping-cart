package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        if (customerDao.existByLoginId(customerRequest.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 id입니다.");
        }
        Customer customer = customerDao.save(CustomerRequest.toCustomer(customerRequest));
        return toCustomerResponse(customer);
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getLoginId(), customer.getUsername());
    }
}
