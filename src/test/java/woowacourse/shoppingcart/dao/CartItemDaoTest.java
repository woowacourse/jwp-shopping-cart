package woowacourse.shoppingcart.dao;

import static Fixture.CustomerFixtures.*;
import static Fixture.ProductFixtures.*;
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
        Long chickenId = productDao.save(CHICKEN);

        Long cartId = cartItemDao.addCartItem(yaho.getId(), chickenId, 10);

        assertThat(cartId).isNotNull();
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {
        Customer mat = customerDao.save(MAT);
        Long chickenId = productDao.save(CHICKEN);
        Long beerId = productDao.save(BEER);
        cartItemDao.addCartItem(mat.getId(), chickenId, 10);
        cartItemDao.addCartItem(mat.getId(), beerId, 10);

        List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(mat.getId());

        assertThat(productsIds).containsExactly(chickenId, beerId);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {
        Customer yaho = customerDao.save(YAHO);
        Long chickenId = productDao.save(CHICKEN);
        Long beerId = productDao.save(BEER);
        Long bananaCartId = cartItemDao.addCartItem(yaho.getId(), chickenId, 10);
        Long appleCartId = cartItemDao.addCartItem(yaho.getId(), beerId, 10);

        List<Long> cartIds = cartItemDao.findIdsByCustomerId(yaho.getId());

        assertThat(cartIds).containsExactly(bananaCartId, appleCartId);
    }

    @DisplayName("cartId를 입력하면 해당 장바구니를 삭제한다.")
    @Test
    void deleteCartItem() {
        Customer mat = customerDao.save(MAT);
        Long chickenId = productDao.save(CHICKEN);
        Long beerId = productDao.save(BEER);
        cartItemDao.addCartItem(mat.getId(), chickenId, 10);
        Long appleCartId = cartItemDao.addCartItem(mat.getId(), beerId, 10);

        cartItemDao.deleteCartItem(appleCartId);

        List<Long> productIds = cartItemDao.findProductIdsByCustomerId(mat.getId());

        assertThat(productIds).containsExactly(chickenId);
    }

    @DisplayName("cartId 에 해당하는 상품의 개수를 quantity로 수정한다.")
    @Test
    void updateQuantity() {
        Customer mat = customerDao.save(MAT);
        Long chickenId = productDao.save(CHICKEN);
        Long bananaCartId = cartItemDao.addCartItem(mat.getId(), chickenId, 10);

        cartItemDao.updateQuantity(bananaCartId, 5, mat.getId());

        Integer quantity = cartItemDao.findQuantityById(bananaCartId).getAsInt();
        assertThat(quantity).isEqualTo(5);
    }
}
