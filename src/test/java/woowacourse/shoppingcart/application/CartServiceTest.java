package woowacourse.shoppingcart.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@SuppressWarnings("NonAsciiChracters")
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
class CartServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    private String customerName;

    private Long productId1;
    private Long productId2;

    @BeforeEach
    void setUp() {
        CustomerRequest customerRequest = new CustomerRequest("angie", "angel", "12345678aA!");
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
        customerName = customerResponse.getName();

        productId1 = productService.addProduct(new Product("치킨", 10_000, "http://example.com/chicken.jpg"));
        productId2 = productService.addProduct(new Product("맥주", 20_000, "http://example.com/beer.jpg"));
    }

    @DisplayName("findCartsByCustomerName 메서드는 회원의 장바구니 목록을 조회한다.")
    @Nested
    class FindCartsByCustomerNameTest {

        @Test
        void 장바구니_조회_성공() {
            cartService.addCart(productId1, customerName);
            cartService.addCart(productId2, customerName);

            List<CartResponse> actual = cartService.findCartsByCustomerName(customerName);

            assertAll(
                    () -> assertThat(actual).hasSize(2),

                    () -> assertThat(actual.get(0)).extracting("productId", "name", "price", "imageUrl")
                            .containsExactly(productId1, "치킨", 10_000, "http://example.com/chicken.jpg"),

                    () -> assertThat(actual.get(1)).extracting("productId", "name", "price", "imageUrl")
                            .containsExactly(productId2, "맥주", 20_000, "http://example.com/beer.jpg")
            );
        }

        @Test
        void 장바구니에_아무런_상품이_없으면_empty_list_반환() {
            List<CartResponse> actual = cartService.findCartsByCustomerName(customerName);

            assertThat(actual).hasSize(0);
        }
    }

    @DisplayName("addCart 메서드는 회원의 장바구니에 상품을 추가한다.")
    @Nested
    class AddCartTest {

        @Test
        void 장바구니_상품_추가_성공() {
            cartService.addCart(productId1, customerName);

            CartResponse actual = cartService.findCartsByCustomerName(customerName).get(0);

            assertThat(actual).extracting("productId", "name", "price", "imageUrl")
                    .containsExactly(productId1, "치킨", 10_000, "http://example.com/chicken.jpg");
        }

        @Test
        void 장바구니_상품이_존재하지_않으면_예외_반환() {
            Long invalidProductId = -1L;

            assertThatThrownBy(() -> cartService.addCart(invalidProductId, customerName))
                    .isInstanceOf(InvalidProductException.class);
        }
    }

    @DisplayName("deleteCart 메서드는 회원의 장바구니의 상품을 제거한다.")
    @Nested
    class DeleteCartTest {

        @Test
        void 장바구니_상품_제거_성공() {
            Long cartId1 = cartService.addCart(productId1, customerName);
            cartService.addCart(productId2, customerName);

            cartService.deleteCart(customerName, cartId1);

            List<CartResponse> actual = cartService.findCartsByCustomerName(customerName);

            assertAll(
                    () -> assertThat(actual).hasSize(1),
                    () -> assertThat(actual.get(0)).extracting("productId", "name", "price", "imageUrl")
                            .containsExactly(productId2, "맥주", 20_000, "http://example.com/beer.jpg")
            );
        }

        @Test
        void 장바구니에_상품이_존재하지_않으면_예외_반환() {
            Long invalidCartId = -1L;

            assertThatThrownBy(() -> cartService.deleteCart(customerName, invalidCartId))
                    .isInstanceOf(NotInCustomerCartItemException.class);
        }
    }

    @DisplayName("deleteAllCart 메서드는 회원의 장바구니의 모든 상품을 제거한다.")
    @Nested
    class DeleteAllCartTest {

        @Test
        void 장바구니_상품_모두_제거_성공() {
            cartService.addCart(productId1, customerName);
            cartService.addCart(productId2, customerName);

            cartService.deleteAllCart(customerName);

            List<CartResponse> actual = cartService.findCartsByCustomerName(customerName);

            assertThat(actual).hasSize(0);
        }
    }
}
