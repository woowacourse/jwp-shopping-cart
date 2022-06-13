package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.domain.Customer;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer findByUsername(String username);

    Customer findByEmail(String email);

    boolean existByUsername(String username);

    boolean existByEmail(String email);

    boolean isValidPasswordByUsername(String username, String password);

    boolean isValidPasswordByEmail(String email, String password);

    void updatePassword(Long id, String password);

    void deleteByUsername(String username);
}
