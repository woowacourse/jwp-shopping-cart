package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.cartitem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.cartitem.CartItemAddResponse;
import woowacourse.shoppingcart.dto.cartitem.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.cartitem.CartItemCreateResponse;
import woowacourse.shoppingcart.dto.cartitem.CartItemResponse;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class CartItemServiceTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductDao productDao;

    private CartItemService cartItemService;

    @BeforeEach
    void setUp() {
        cartItemService = new CartItemService(cartItemDao, customerDao, productDao);
        productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productDao.save(new Product("apple", 2_000, "woowa2.com"));
        productDao.save(new Product("cheeze", 3_000, "woowa2.com"));
    }

    @DisplayName("장바구니에 물건을 처음으로 추가한다.")
    @Test
    void createCartItems() {
        //given
        TokenRequest tokenRequest = new TokenRequest("1");
        List<CartItemCreateRequest> cartItemCreateRequests =
                List.of(new CartItemCreateRequest(1L), new CartItemCreateRequest(2L));

        //when
        List<CartItemCreateResponse> cartItemCreateResponses =
                cartItemService.createCartItems(tokenRequest, cartItemCreateRequests);

        //then
        cartItemCreateResponses
                .forEach(response -> assertThat(response.getAddedQuantity()).isEqualTo(1));
    }

    @DisplayName("장바구니에 있는 물건의 개수를 추가한다.")
    @Test
    void addCartItem() {
        //given
        TokenRequest tokenRequest = new TokenRequest("1");
        List<CartItemCreateRequest> cartItemCreateRequests =
                List.of(new CartItemCreateRequest(1L), new CartItemCreateRequest(2L));
        cartItemService.createCartItems(tokenRequest, cartItemCreateRequests);

        //when
        CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(1L, 3);
        CartItemAddResponse cartItemAddResponse = cartItemService.addCartItem(tokenRequest, cartItemAddRequest);

        //then
        assertThat(cartItemAddResponse.getQuantity()).isEqualTo(3);
    }

    @DisplayName("장바구니에 담은 상품 목록을 가져온다.")
    @Test
    void findCartItemsByCustomerId() {
        //given
        TokenRequest tokenRequest = new TokenRequest("1");
        List<CartItemCreateRequest> cartItemCreateRequests =
                List.of(new CartItemCreateRequest(1L), new CartItemCreateRequest(2L));
        cartItemService.createCartItems(tokenRequest, cartItemCreateRequests);

        //when
        CartItemResponse cartItemResponse = cartItemService.findCartItemsByCustomerId(tokenRequest).get(0);

        //then
        assertAll(
                () -> assertThat(cartItemResponse.getId()).isEqualTo(1L),
                () -> assertThat(cartItemResponse.getProductId()).isEqualTo(1L),
                () -> assertThat(cartItemResponse.getName()).isEqualTo("banana"),
                () -> assertThat(cartItemResponse.getPrice()).isEqualTo(1_000),
                () -> assertThat(cartItemResponse.getImageUrl()).isEqualTo("woowa1.com"),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(1)
        );
    }
}
