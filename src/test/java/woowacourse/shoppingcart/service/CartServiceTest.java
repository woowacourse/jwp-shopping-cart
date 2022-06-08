package woowacourse.shoppingcart.service;

import org.assertj.core.api.Assertions;
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
import woowacourse.shoppingcart.domain.Customer.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.request.UpdateCartItemCountItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponseDto;

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
        AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId, 1);
        cartService.addCart(addCartItemRequestDto, customerId);

        List<CartItemResponseDto> cartItems = cartService.findCartsByCustomerId(customerId);

        assertThat(cartItems.size()).isEqualTo(1);
        assertThat(cartItems.get(0).getName()).isEqualTo(PRODUCT_NAME);
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
    @DisplayName("장바구니 내의 품목을 장바구니에서 제거한다.")
    void deleteCart() {
        cartItemDao.addCartItem(customerId, productId, 1);

        cartService.deleteCart(customerId, productId);

        final List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        assertThat(cartItems.size()).isEqualTo(0);
    }
}
