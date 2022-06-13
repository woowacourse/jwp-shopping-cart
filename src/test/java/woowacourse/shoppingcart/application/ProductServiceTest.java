package woowacourse.shoppingcart.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static woowacourse.fixture.PasswordFixture.encryptedBasicPassword;
import static woowacourse.fixture.ProductFixture.PRODUCT_APPLE;
import static woowacourse.fixture.ProductFixture.PRODUCT_BANANA;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private CartItemDao cartItemDao;

    @DisplayName("등록된 모든 상품을 가져온다.")
    @Test
    void findProducts() {
        // given
        List<Product> products = List.of(PRODUCT_BANANA, PRODUCT_APPLE);
        given(productDao.findProducts())
                .willReturn(products);

        // when
        final List<ProductResponse> productResponses = productService.findProducts();
        final List<Long> productIds = productResponses.stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        // then
        assertAll(
                () -> assertThat(productIds).containsExactly(1L, 2L),
                () -> verify(productDao).findProducts()
        );
    }

    @DisplayName("유저 id를 통해서 등록된 모든 상품을 가져온다.")
    @Test
    void findProductsByCustomerId() {
        // given
        List<Product> products = List.of(PRODUCT_BANANA, PRODUCT_APPLE);
        final Long customerId = 1L;
        final UserName userName = new UserName("giron");
        final Customer customer = new Customer(1L, userName, encryptedBasicPassword);
        final Cart cart = new Cart(1L, new Quantity(5), PRODUCT_BANANA);
        given(customerDao.findById(customerId))
                .willReturn(Optional.of(customer));
        given(productDao.findProducts())
                .willReturn(products);
        given(cartItemDao.findAllJoinProductByCustomerId(customerId))
                .willReturn(List.of(cart));

        // when
        final List<ProductResponse> productResponses = productService.findProductsByCustomerId(customerId);
        final List<Long> productIds = productResponses.stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        final List<Long> cartIds = productResponses.stream()
                .map(ProductResponse::getCartId)
                .collect(Collectors.toList());
        final List<Integer> cartQuantities = productResponses.stream()
                .map(ProductResponse::getQuantity)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(productIds).containsExactly(1L, 2L),
                () -> assertThat(cartIds).containsExactly(1L, null),
                () -> assertThat(cartQuantities).containsExactly(5, 0),
                () -> verify(customerDao).findById(customerId),
                () -> verify(productDao).findProducts(),
                () -> verify(cartItemDao).findAllJoinProductByCustomerId(customerId)
        );
    }

    @DisplayName("등록된 상품을 페이징 처리하여 가져온다.")
    @Test
    void findPageableProducts() {
        // given
        given(productDao.findPageableProducts(1, 0))
                .willReturn(List.of(PRODUCT_BANANA));

        // when
        final List<ProductResponse> firstProductResponses = productService.findPageableProducts(1, 0);
        final List<Long> productIds = firstProductResponses.stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(productIds).containsExactly(1L),
                () -> verify(productDao).findPageableProducts(1, 0)
        );
    }

    @DisplayName("유저 id를 통해서 등록된 상품을 페이징 처리하여 가져온다.")
    @Test
    void findPageableProductsByCustomerId() {
        // given
        final Long customerId = 1L;
        final UserName userName = new UserName("giron");
        final Customer customer = new Customer(1L, userName, encryptedBasicPassword);
        final Cart cart = new Cart(1L, new Quantity(5), PRODUCT_BANANA);
        given(customerDao.findById(customerId))
                .willReturn(Optional.of(customer));
        given(productDao.findPageableProducts(1, 0))
                .willReturn(List.of(PRODUCT_BANANA));
        given(cartItemDao.findAllJoinProductByCustomerId(customerId))
                .willReturn(List.of(cart));

        // when
        final List<ProductResponse> productResponses =
                productService.findPageableProductsByCustomerId(1, 0, customerId);
        final List<Long> productIds = productResponses.stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        final List<Long> cartIds = productResponses.stream()
                .map(ProductResponse::getCartId)
                .collect(Collectors.toList());
        final List<Integer> cartQuantities = productResponses.stream()
                .map(ProductResponse::getQuantity)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(productIds).containsExactly(1L),
                () -> assertThat(cartIds).containsExactly(1L),
                () -> assertThat(cartQuantities).containsExactly(5),
                () -> verify(customerDao).findById(customerId),
                () -> verify(productDao).findPageableProducts(1, 0),
                () -> verify(cartItemDao).findAllJoinProductByCustomerId(customerId)
        );
    }
}
