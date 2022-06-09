package woowacourse.cartitem.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.cartitem.domain.CartItem;
import woowacourse.customer.dao.CustomerDao;
import woowacourse.customer.domain.Customer;
import woowacourse.product.dao.ProductDao;
import woowacourse.product.domain.Price;
import woowacourse.product.domain.Product;
import woowacourse.product.domain.Stock;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final ProductDao productDao;
    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;
    private final DataSource dataSource;

    private Long customerId;
    private Long productId1;
    private Long productId2;

    public CartItemDaoTest(final DataSource dataSource) {
        this.dataSource = dataSource;
        customerDao = new CustomerDao(dataSource);
        productDao = new ProductDao(dataSource);
        cartItemDao = new CartItemDao(dataSource);
    }

    @BeforeEach
    void setUp() {
        customerId = customerDao.save(Customer.of("jjanggu", "password1234", "01000001111", "test")).getId();
        productId1 = productDao.save(new Product("banana", new Price(1_000), new Stock(10), "woowa1.com"));
        productId2 = productDao.save(new Product("apple", new Price(2_000), new Stock(20), "woowa2.com"));
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // when
        final Long cartId = cartItemDao.save(customerId, productId1, 5);

        // then
        assertThat(cartId).isNotNull();
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {
        // given
        cartItemDao.save(customerId, productId1, 5);
        cartItemDao.save(customerId, productId2, 5);

        // when
        final List<Long> productsIds = cartItemDao.findAllByCustomerId(customerId)
            .stream()
            .map(CartItem::getProductId)
            .collect(Collectors.toList());

        // then
        assertThat(productsIds).containsExactly(productId1, productId2);
    }

    @DisplayName("해당 커스터머가 담은 아이템의 수량을 수정한다.")
    @Test
    void updateQuantity() {
        // given
        final Long cartItemId = cartItemDao.save(customerId, productId1, 5);

        // when
        final CartItem cartItem = cartItemDao.findById(cartItemId).get();
        cartItem.updateQuantity(10);
        cartItemDao.update(cartItemId, cartItem);

        // then
        assertThat(cartItemDao.findById(cartItemId).get().getQuantity().getValue()).isEqualTo(10);
    }

    @DisplayName("특정 아이템을 삭제한다.")
    @Test
    void deleteCartItem() {
        // given
        final Long cartItemId = cartItemDao.save(customerId, productId1, 5);

        // when
        cartItemDao.deleteByIdAndCustomerId(cartItemId, customerId);

        // then
        assertThat(cartItemDao.findById(cartItemId)).isEmpty();
    }
}
