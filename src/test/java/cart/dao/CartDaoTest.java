package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.CartEntity;
import cart.entity.customer.CustomerEntity;
import cart.entity.product.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;


@JdbcTest
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;
    private ProductDao productDao;
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("고객의 ID와 상품의 ID를 가진 카트 데이터를 저장한다.")
    @Test
    void save() {
        //given
        final Long savedCustomerId = customerDao.save(new CustomerEntity(null, "email@email.com", "password"));
        final Long savedProductId = productDao.save(new ProductEntity("name", "imageUrl", 1000, "description"));

        //when
        cartDao.save(new CartEntity(savedCustomerId, savedProductId));

        //then
        final List<Long> allProductIds = cartDao.findAllProductIdsBy(savedCustomerId);
        assertThat(allProductIds).containsExactly(savedProductId);
    }

    @DisplayName("카드의 ID로 카트 데이터를 삭제한다.")
    @Test
    void delete() {
        //given
        final Long savedCustomerId = customerDao.save(new CustomerEntity(null, "email@email.com", "password"));
        final Long savedProductId = productDao.save(new ProductEntity("name", "imageUrl", 1000, "description"));
        final Long savedCartId = cartDao.save(new CartEntity(savedCustomerId, savedProductId));

        //when
        cartDao.delete(savedCartId);

        //then
        final List<Long> allProductIds = cartDao.findAllProductIdsBy(savedCustomerId);
        assertThat(allProductIds).isEmpty();
    }
}
