package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static woowacourse.utils.Fixture.customer_id없음;
import static woowacourse.utils.Fixture.맥주_id없음;
import static woowacourse.utils.Fixture.치킨_id없음;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import woowacourse.shoppingcart.domain.cart.Cart;

@JdbcTest
public class CartDaoTest {

    @Autowired
    private DataSource dataSource;
    private CartDao cartDao;
    private ProductDao productDao;
    private CustomerDao customerDao;

    private Long savedProductId1;
    private Long savedProductId2;
    private Long savedCustomerId1;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(dataSource);
        productDao = new ProductDao(dataSource);
        customerDao = new CustomerDao(dataSource);

        savedProductId1 = productDao.save(치킨_id없음).getId();
        savedProductId2 = productDao.save(맥주_id없음).getId();
        savedCustomerId1 = customerDao.save(customer_id없음).getId();
    }

    @DisplayName("장바구니에 물건을 저장한다.")
    @Test
    void saveCart() {
        // given
        Cart cart = new Cart(savedProductId1, savedCustomerId1, 1000);

        // when
        Cart saveCart = cartDao.save(cart);

        // then
        assertThat(saveCart).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(cart);
    }

    @DisplayName("장바구니에 productId와 일치하는 product의 수량을 갱신한다.")
    @Test
    void update() {
        // given
        Cart cart = new Cart(savedProductId1, savedCustomerId1, 1000);
        cartDao.save(cart);
        Cart cart2 = new Cart(savedProductId1, savedCustomerId1, 2000);

        // when
        Cart updated = cartDao.update(cart2);

        // then
        assertThat(updated).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(cart2);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니를 조회한다.")
    @Test
    void findByCustomerIdAndProductId() {
        // given
        Cart cart1 = new Cart(savedProductId1, savedCustomerId1, 1000);
        cartDao.save(cart1);
        Cart cart2 = new Cart(savedProductId2, savedCustomerId1, 1000);
        cartDao.save(cart2);

        List<Cart> carts = cartDao.findByCustomerId(savedCustomerId1);

        assertThat(carts).hasSize(2)
                .extracting("productId", "customerId", "quantity")
                .containsExactly(
                        tuple(cart1.getProductId(), cart1.getCustomerId(), cart1.getQuantity()),
                        tuple(cart2.getProductId(), cart2.getCustomerId(), cart2.getQuantity())
                );
    }
}
