package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ThumbnailImageDto;

@SpringBootTest
@Sql("/truncate.sql")
public class CartItemServiceTest {

    public static final String EMAIL = "email@email.com";
    private final CustomerService customerService;
    private final ProductService productService;
    private final CartItemService cartItemService;

    @Autowired
    public CartItemServiceTest(CustomerService customerService,
        ProductService productService,
        CartItemService cartItemService) {
        this.customerService = customerService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @BeforeEach
    void setUp() {
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, "password1!", "azpi");
        ProductRequest productRequest = new ProductRequest("워치", 10_000, 10, new ThumbnailImageDto("url", "alt"));

        customerService.save(customerRequest);
        productService.addProduct(productRequest);
    }

    @DisplayName("상품을 장바구니에 담는다")
    @Test
    void addCartItem() {
        // given
        CartItemRequest cartItemRequest = new CartItemRequest(1L, 10);

        // when
        CartItemResponse cartItemResponse = cartItemService.addCart(EMAIL, cartItemRequest);

        // then
        assertThat(cartItemResponse)
            .extracting("productId", "price", "name", "quantity")
            .containsExactly(cartItemRequest.getProductId(), 10_000, "워치",
                cartItemRequest.getQuantity());
    }
}
