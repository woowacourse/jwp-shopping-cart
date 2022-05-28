package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long save(CustomerCreateRequest request) {
        return customerDao.save(request.toEntity());
    }

    public Customer findById(long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    public void update(Long id, CustomerUpdateRequest request) {
        validateCustomerExists(id);
        customerDao.update(id, request.getUsername());
    }

    public void delete(Long id) {
        validateCustomerExists(id);
        customerDao.delete(id);
    }

    private void validateCustomerExists(Long id) {
        findById(id);
    }
}
