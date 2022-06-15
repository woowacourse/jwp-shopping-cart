package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;

import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.shoppingcart.ProductInsertUtil;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private Long customerId;
    private Product product;

    private final CartItemDao cartItemDao;
    private final ProductInsertUtil productInsertUtil;
    private final CustomerDao customerDao;

    public CartItemDaoTest(DataSource dataSource) {
        cartItemDao = new CartItemDao(dataSource, new ProductDao(dataSource));
        productInsertUtil = new ProductInsertUtil(dataSource);
        customerDao = new CustomerDao(dataSource);
    }

    @BeforeEach
    void init() {
        customerId = customerDao.save(Customer.builder()
            .nickname("does")
            .email("asd@gmail.com")
            .password("1asd!")
            .build())
            .getId();
        Long productId = productInsertUtil.insert("banana", 1_000, "https://www.woowa1.com");
        product = new Product(productId, "banana", 1_000, "https://woowa1.com");
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다.")
    @Test
    void addCartItem() {
        // given
        CartItem cartItem = new CartItem(product, 2);

        // when
        CartItem saved = cartItemDao.save(customerId, cartItem);

        // then
        assertThat(saved.getId()).isNotNull();
    }

    @DisplayName("customer id로 cartItem을 조회한다.")
    @Test
    void getItems() {
        // given
        cartItemDao.save(customerId, new CartItem(product, 3));

        Long productId2 = productInsertUtil.insert("apple", 1000, "https://woowa1.com");
        Product product2 = new Product(productId2, "apple", 1000, "https://woowa1.com");
        cartItemDao.save(customerId, new CartItem(product2, 2));

        // when
        List<CartItem> items = cartItemDao.findByCustomerId(customerId);

        // then
        assertThat(items.size()).isEqualTo(2);
        assertThat(items)
            .map(CartItem::getName)
            .containsOnly("banana", "apple");
    }

    @DisplayName("cartItem 정보를 수정한다.")
    @Test
    void update() {
        // given
        CartItem item = cartItemDao.save(customerId, new CartItem(product, 3));

        // when
        cartItemDao.update(new CartItem(item.getId(), product, 10));

        // then
        CartItem updated = cartItemDao.findByCustomerId(customerId).get(0);
        assertThat(updated.getId()).isEqualTo(item.getId());
        assertThat(updated.getQuantity()).isEqualTo(10);
    }

    @DisplayName("id 리스트를 받아 해당하는 item을 전부 삭제한다.")
    @Test
    void deleteAll() {
        // given
        Long cartItemId1 = cartItemDao.save(customerId, new CartItem(product, 3))
            .getId();

        Long productId2 = productInsertUtil.insert("apple", 1000, "https://woowa1.com");
        Product product2 = new Product(productId2, "apple", 1000, "https://woowa1.com");
        Long cartItemId2 = cartItemDao.save(customerId, new CartItem(product2, 2))
            .getId();

        // when
        cartItemDao.deleteAll(List.of(cartItemId1, cartItemId2));

        // then
        assertThat(cartItemDao.findByCustomerId(customerId)).isEmpty();
    }
}
