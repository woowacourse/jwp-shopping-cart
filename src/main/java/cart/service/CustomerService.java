package cart.service;

import cart.entity.CustomerEntity;
import cart.repository.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {

    @Autowired
    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<CustomerEntity> findAll() {
        return customerDao.findAll();
    }
}
