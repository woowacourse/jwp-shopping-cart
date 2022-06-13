package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.exception.DuplicateCartItemException;
import woowacourse.shoppingcart.exception.InvalidAccountException;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@SpringBootTest
@Transactional
class CartServiceTest {

    private static final String EMAIL = "email@email.com";
    private static final String NOT_FOUND_EMAIL = "not_found@email.com";

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

    private Product 칫솔;
    private Product 치약;
    private long notFoundProductId;

    @BeforeEach
    void setUp() {
        accountService.saveAccount(new SignUpRequest(EMAIL, "12345678a", "tonic"));

        칫솔 = productService.save(new ProductRequest("칫솔", 1200, "image 칫솔"));
        치약 = productService.save(new ProductRequest("치약", 2300, "image 치약"));
        notFoundProductId = Stream.of(칫솔, 치약).mapToLong(Product::getId).max().orElse(0L) + 1L;
    }

    @DisplayName("존재하지 않는 사용자 ID로 상품 추가시 예외 발생")
    @Test
    void addProductWithNotFoundAccountId() {
        assertThatThrownBy(() -> cartService.addProduct(NOT_FOUND_EMAIL, 칫솔.getId()))
            .isInstanceOf(InvalidAccountException.class);
    }

    @DisplayName("존재하지 않는 상품 추가 시 예외 발생")
    @Test
    void addNotFoundProduct() {
        assertThatThrownBy(() -> cartService.addProduct(EMAIL, notFoundProductId))
            .isInstanceOf(NotFoundProductException.class);
    }

    @DisplayName("상품 장바구니에 추가")
    @Test
    void addProduct() {
        cartService.addProduct(EMAIL, 칫솔.getId());

        List<CartItem> cartItems = cartService.findCartsByEmail(EMAIL);

        assertThat(cartItems)
            .extracting(
                cartItem -> cartItem.getProduct().getId(),
                cartItem -> cartItem.getProduct().getName(),
                cartItem -> cartItem.getProduct().getPrice(),
                cartItem -> cartItem.getProduct().getImageUrl(),
                CartItem::getQuantity)
            .containsExactly(tuple(칫솔.getId(), 칫솔.getName(), 칫솔.getPrice(), 칫솔.getImageUrl(), 1));
    }

    @DisplayName("중복된 상품 장바구니 추가 시 예외 발생")
    @Test
    void addDuplicateProduct() {
        cartService.addProduct(EMAIL, 칫솔.getId());

        assertThatThrownBy(() -> cartService.addProduct(EMAIL, 칫솔.getId()))
            .isInstanceOf(DuplicateCartItemException.class);
    }

    @DisplayName("존재하지 않는 사용자로 장바구니 조회 시 예외 발생")
    @Test
    void findCartItemsByNotExistAccount() {
        assertThatThrownBy(() -> cartService.findCartsByEmail(NOT_FOUND_EMAIL))
            .isInstanceOf(InvalidAccountException.class);
    }
}