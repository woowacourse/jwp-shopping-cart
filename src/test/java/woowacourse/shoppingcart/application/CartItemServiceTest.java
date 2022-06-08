package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.cart.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.cart.CartItemDto;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.product.ProductCreateRequest;
import woowacourse.shoppingcart.exception.ExistSameProductIdException;
import woowacourse.shoppingcart.exception.NoSuchProductException;
import woowacourse.shoppingcart.exception.OutOfStockException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemServiceTest {

    private final CartItemService cartItemService;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final CustomerDao customerDao;

    public CartItemServiceTest(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                               JdbcTemplate jdbcTemplate) {
        this.cartItemDao = new CartItemDao(jdbcTemplate);
        this.productDao = new ProductDao(jdbcTemplate);
        this.customerDao = new CustomerDao(dataSource, namedParameterJdbcTemplate);

        this.cartItemService = new CartItemService(cartItemDao, productDao);
    }

    @DisplayName("CartItem을 추가하고 조회한다.")
    @Test
    void addCartItemAndFindCartsByCustomerId() {
        // given
        Long customerId = 손님_추가한다("손1@naver.com", "손님1", "1234");
        Long productId1 = 상품_추가한다("아이템1", 10000, "주소1", 100);
        Long productId2 = 상품_추가한다("아이템2", 20000, "주소2", 200);

        // when
        장바구니에_추가한다(customerId, productId1, 3);
        장바구니에_추가한다(customerId, productId2, 10);

        // then
        List<CartItemDto> carts = 장바구니_목록을_조회한다(customerId);
        assertThat(carts).hasSize(1);
    }

    @DisplayName("CartItem을 추가 시에 존재하지 않는 product id로 요청을 보내면 예외가 발생한다.")
    @Test
    void addCartItem_throwNoProductId() {
        // given
        Long customerId = 손님_추가한다("손1@naver.com", "손님1", "1234");
        Long productId1 = 상품_추가한다("아이템1", 10000, "주소1", 100);
        Long productId2 = 상품_추가한다("아이템2", 20000, "주소2", 200);

        // when then
        assertThatThrownBy(() -> 장바구니에_추가한다(customerId, 100L, 10))
                .isInstanceOf(NoSuchProductException.class);
    }

    @DisplayName("CartItem을 추가 시에 이미 담겨있는 product id로 요청을 보내면 예외가 발생한다.")
    @Test
    void addCartItem_throwSameProductId() {
        // given
        Long customerId = 손님_추가한다("손1@naver.com", "손님1", "1234");
        Long productId = 상품_추가한다("아이템1", 10000, "주소1", 100);
        장바구니에_추가한다(customerId, productId, 10);

        // when then
        assertThatThrownBy(() -> 장바구니에_추가한다(customerId, productId, 10))
                .isInstanceOf(ExistSameProductIdException.class);
    }

    @DisplayName("CartItem을 추가 시에 재고보다 많은 개수의 요청을 보내면 예외가 발생한다.")
    @Test
    void addCartItem_throwOutOfStock() {
        // given
        Long customerId = 손님_추가한다("손1@naver.com", "손님1", "1234");
        Long productId = 상품_추가한다("아이템1", 10000, "주소1", 10);

        // when then
        assertThatThrownBy(() -> 장바구니에_추가한다(customerId, productId, 100))
                .isInstanceOf(OutOfStockException.class);
    }

    private List<CartItemDto> 장바구니_목록을_조회한다(Long customerId) {
        return cartItemService.findCartsByCustomerId(customerId);
    }

    private Long 장바구니에_추가한다(Long customerId, Long productId, int count) {
        return cartItemService.addCartItem(customerId, new CartItemCreateRequest(productId, count));
    }

    private Long 상품_추가한다(String name, int price, String imageUrl, int quantity) {
        return productDao.save(new ProductCreateRequest(name, price, imageUrl, quantity));
    }

    private Long 손님_추가한다(String email, String username, String password) {
        return customerDao.save(new CustomerCreateRequest(email, username, password));
    }


}
