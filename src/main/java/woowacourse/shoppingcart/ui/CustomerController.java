package woowacourse.shoppingcart.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerSaveRequest;

@RestController
public class CustomerController {

    private final CustomerDao customerDao;

    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @PostMapping("/api/customers")
    public ResponseEntity<Void> save(@RequestBody CustomerSaveRequest request) {
        Customer customer = customerDao.save(request.toCustomer());
        return ResponseEntity.created(URI.create("/api/customers/" + customer.getId())).build();
    }
}
