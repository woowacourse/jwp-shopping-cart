package woowacourse.shoppingcart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.request.UpdateCartItemCountItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponseDto;
import woowacourse.shoppingcart.exception.DuplicateCartItemException;
import woowacourse.shoppingcart.exception.NotFoundProductException;
import woowacourse.shoppingcart.exception.OverQuantityException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static woowacourse.fixture.Fixture.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CartServiceTest {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ProductDao productDao;

    private Long customerId;
    private Long productId;

    @BeforeEach
    void setUp(){
        customerId = customerDao.save(Customer.createWithoutId(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        productId = productDao.save(new Product(PRODUCT_NAME, PRICE, THUMBNAIL_URL, QUANTITY));
    }

    @Test
    @DisplayName("장바구니에 품목을 추가한다.")
    void addCart() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId, 1);
        cartService.addCart(addCartItemRequestDto, customerId);

        final List<CartItemResponseDto> cartItems = cartService.findCartsByCustomerId(customerId);

        assertThat(cartItems.size()).isEqualTo(1);
        assertThat(cartItems.get(0).getName()).isEqualTo(PRODUCT_NAME);
    }

    @Test
    @DisplayName("장바구니에 품목을 추가할때 이미 등록된 품목일 경우 예외가 발생한다.")
    void addCart_DuplicateProductException() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId, 1);
        cartService.addCart(addCartItemRequestDto, customerId);

        assertThatThrownBy(() -> cartService.addCart(addCartItemRequestDto, customerId))
                        .isInstanceOf(DuplicateCartItemException.class)
                        .hasMessage("이미 담겨있는 상품입니다.");
    }

    @Test
    @DisplayName("장바구니에 품목을 추가할때 재고가 주문수량보다 적으면 예외가 발생한다.")
    void addCart_OverQuantityException() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId, 11);
        assertThatThrownBy(() -> cartService.addCart(addCartItemRequestDto, customerId))
                        .isInstanceOf(OverQuantityException.class)
                        .hasMessage("재고가 부족합니다.");
    }


    @Test
    @DisplayName("장바구니 내의 품목의 수량을 수정한다.")
    void updateCart() {
        cartItemDao.addCartItem(customerId, productId, 1);

        cartService.updateCart(customerId, productId, new UpdateCartItemCountItemRequest(2));

        final CartItem cartItem = cartItemDao.findCartItemByCustomerIdAndProductId(customerId, productId).get();
        assertThat(cartItem.getCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 내의 품목 수량을 수정할때 존재하지 않는 품목이면 예외가 발생한다.")
    void updateCart_NotFoundProductException() {
        cartItemDao.addCartItem(customerId, productId, 1);

        assertThatThrownBy(() -> cartService.updateCart(customerId, 2L, new UpdateCartItemCountItemRequest(2)))
                        .isInstanceOf(NotFoundProductException.class)
                        .hasMessage("존재하지 않는 상품 ID입니다.");
    }

    @Test
    @DisplayName("장바구니 내의 품목 수량을 수정할때 재고가 주문수량보다 적을 경우 예외가 발생한다.")
    void updateCart_() {
        cartItemDao.addCartItem(customerId, productId, 1);

        assertThatThrownBy(() -> cartService.updateCart(customerId, productId, new UpdateCartItemCountItemRequest(11)))
                        .isInstanceOf(OverQuantityException.class)
                        .hasMessage("재고가 부족합니다.");
    }

    @Test
    @DisplayName("장바구니 내의 품목을 장바구니에서 제거한다.")
    void deleteCart() {
        cartItemDao.addCartItem(customerId, productId, 1);

        cartService.deleteCart(customerId, productId);

        final List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        assertThat(cartItems.size()).isEqualTo(0);
    }
}
