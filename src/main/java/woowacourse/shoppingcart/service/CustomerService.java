package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long signUp(final SignUpDto signUpDto){
        final Customer newCustomer = Customer.createWithoutId(
                signUpDto.getEmail(),
                signUpDto.getPassword(),
                signUpDto.getUsername()
        );

        return customerDao.save(newCustomer);
    }

    public Customer findCustomerById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }
}
