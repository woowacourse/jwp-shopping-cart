package cart.service;

import cart.dao.CustomerDao;
import cart.entity.customer.CustomerEntity;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<CustomerEntity> findAll() {
        return customerDao.findAll();
    }
}
