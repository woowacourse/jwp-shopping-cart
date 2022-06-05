package woowacourse.shoppingcart.infra;

import java.util.Optional;
import woowacourse.shoppingcart.domain.Customer;

public interface CustomerRepository {
    Customer save(Customer Customer);

    Optional<Customer> findById(long id);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByName(String name);

    void deleteById(long id);
}
