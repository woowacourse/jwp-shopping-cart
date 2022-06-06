package woowacourse.shoppingcart.dao;

import static Fixture.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.product.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.customerDao = new CustomerDao(jdbcTemplate);
        this.cartItemDao = new CartItemDao(jdbcTemplate);
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        Customer yaho = customerDao.save(YAHO);
        Long bananaId = productDao.save(new Product("banana", 1_000, "httpwoowa1.com", false));

        Long cartId = cartItemDao.addCartItem(yaho.getId(), bananaId);

        assertThat(cartId).isNotNull();
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {
        Customer mat = customerDao.save(MAT);
        Long bananaId = productDao.save(new Product("banana", 1_000, "httpwoowa1.com", false));
        Long appleId = productDao.save(new Product("apple", 2_000, "httpwoowa2.com", false));
        cartItemDao.addCartItem(mat.getId(), bananaId);
        cartItemDao.addCartItem(mat.getId(), appleId);

        List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(mat.getId());

        assertThat(productsIds).containsExactly(bananaId, appleId);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {
        Customer yaho = customerDao.save(YAHO);
        Long bananaId = productDao.save(new Product("banana", 1_000, "httpwoowa1.com", false));
        Long appleId = productDao.save(new Product("apple", 2_000, "httpwoowa2.com", false));
        Long bananaCartId = cartItemDao.addCartItem(yaho.getId(), bananaId);
        Long appleCartId = cartItemDao.addCartItem(yaho.getId(), appleId);

        List<Long> cartIds = cartItemDao.findIdsByCustomerId(yaho.getId());

        assertThat(cartIds).containsExactly(bananaCartId, appleCartId);
    }

    @DisplayName("cartId를 입력하면 해당 장바구니를 삭제한다.")
    @Test
    void deleteCartItem() {
        Customer mat = customerDao.save(MAT);
        Long bananaId = productDao.save(new Product("banana", 1_000, "httpwoowa1.com", false));
        Long appleId = productDao.save(new Product("apple", 2_000, "httpwoowa2.com", false));
        cartItemDao.addCartItem(mat.getId(), bananaId);
        Long appleCartId = cartItemDao.addCartItem(mat.getId(), appleId);

        cartItemDao.deleteCartItem(appleCartId);

        List<Long> productIds = cartItemDao.findProductIdsByCustomerId(mat.getId());

        assertThat(productIds).containsExactly(bananaId);
    }
}
