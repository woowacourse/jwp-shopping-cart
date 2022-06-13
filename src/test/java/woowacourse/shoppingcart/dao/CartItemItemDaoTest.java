package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartProductResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import javax.sql.DataSource;
import java.util.List;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemItemDaoTest {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItemItemDaoTest(DataSource dataSource) {
        cartItemDao = new CartItemDao(dataSource);
        productDao = new ProductDao(dataSource);
    }

    @DisplayName("장바구니 물건 추가")
    @Test
    void addCartItem() {
        // given
        Product product = productDao.save(new Product("apple", 2_000, "woowa2.com"));

        // when
        final Long cartId = cartItemDao.addCartItem(1L, product.getId(), 1L, true);

        // then
        assertThat(cartId).isNotNull();
    }

    @DisplayName("카트 아이템 번호로 아이템을 찾는다. ")
    @Test
    void findProductIdsByCustomerId() {
        // given
        Product product = productDao.save(new Product("apple", 1000, "woowa2.com"));
        final Long cartItemId = cartItemDao.addCartItem(1L, product.getId(), 1L, true);

        // when
        CartItem cartItem = cartItemDao.findCartIdById(cartItemId);

        // then
        assertThat(cartItem.getQuantity()).isEqualTo(1);
    }

    @DisplayName("장바구니 아이템 전체 삭제")
    @Test
    void deleteAll() {
        //given
        Product product = productDao.save(new Product("apple", 1000, "woowa2.com"));
        cartItemDao.addCartItem(1L, product.getId(), 1L, true);

        Product product2 = productDao.save(new Product("apple", 1000, "woowa2.com"));
        final Long cartItemId2 = cartItemDao.addCartItem(1L, product2.getId(), 1L, true);

        //when
        cartItemDao.deleteAll(1L);

        //then
        assertThatThrownBy(() -> cartItemDao.findCartIdById(cartItemId2))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessageContaining("유효하지 않은 장바구니입니다.");

    }

    @DisplayName("장바구니 아이템 복수 삭제")
    @Test
    void deleteByIds() {
        //given
        Product product = productDao.save(new Product("apple", 1000, "woowa2.com"));
        cartItemDao.addCartItem(1L, product.getId(), 1L, true);

        Product product2 = productDao.save(new Product("apple", 1000, "woowa2.com"));
        final Long cartItemId2 = cartItemDao.addCartItem(1L, product2.getId(), 1L, true);

        //when
        cartItemDao.deleteCartItemById(List.of(1L, 2L), 1L);

        //then
        assertThat(cartItemDao.findIdsByCustomerId(1L).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("장바구니 상품 정보 수정 - 단일")
    void updateItem() {
        //given
        Product product = productDao.save(new Product("apple", 1000, "woowa2.com"));
        cartItemDao.addCartItem(1L, product.getId(), 1L, true);

        Product product2 = productDao.save(new Product("apple", 1000, "woowa2.com"));
        Long cartItemId2 = cartItemDao.addCartItem(1L, product2.getId(), 1L, true);

        cartItemDao.updateById(cartItemId2, 1L, 3L, false);

        assertThat(cartItemDao.findCartIdById(cartItemId2).getQuantity()).isEqualTo(3L);
    }

    @Test
    @DisplayName("장바구니 상품 정보 수정 - 복수")
    void updateItems() {
        //given
        Product product = productDao.save(new Product("apple", 1000, "woowa2.com"));
        cartItemDao.addCartItem(1L, product.getId(), 1L, true);

        Product product2 = productDao.save(new Product("apple", 1000, "woowa2.com"));
        Long cartItemId2 = cartItemDao.addCartItem(1L, product2.getId(), 1L, true);

        cartItemDao.updateByIds(1L,
                List.of(new CartItem(1L, product, 2L, false),
                        new CartItem(2L, product, 3L, false)));

        assertThat(cartItemDao.findCartIdById(cartItemId2).getQuantity()).isEqualTo(3L);
    }
}
