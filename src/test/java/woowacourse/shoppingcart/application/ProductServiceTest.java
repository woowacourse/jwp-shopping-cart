package woowacourse.shoppingcart.application;

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
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CustomerWithId;
import woowacourse.shoppingcart.dto.ProductResponse;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;
    @Mock
    private CartItemDao cartItemDao;
    @Mock
    private CustomerDao customerDao;

    @DisplayName("User가 로그인 안되어있으면 ProductRespones에 Cart정보는 없다.")
    @Test
    void findProducts() {
        CustomerWithId user = new CustomerWithId(null);
        List<Product> products = List.of(new Product(1L, "제품1", 5000, "www.imageUrl1.com"),
                new Product(2L, "제품2", 1000, "www.image2.com"));

        given(productDao.findProducts()).willReturn(products);
        List<ProductResponse> productResponses = productService.findAll(user);
        assertAll(
                () -> assertThat(productResponses.get(0).getCartId()).isEqualTo(null),
                () -> assertThat(productResponses.get(0).getQuantity()).isEqualTo(0),
                () -> assertThat(productResponses.get(1).getCartId()).isEqualTo(null),
                () -> assertThat(productResponses.get(1).getQuantity()).isEqualTo(0)
        );
    }

    @DisplayName("User가 로그인 되어있으면 ProductRespones에 Cart정보도 담겨있다.")
    @Test
    void findProductsWithLogin() {
        CustomerWithId user = new CustomerWithId(1L);
        Customer customer = new Customer(1L, "giron", "passs3w12");

        List<Product> products = List.of(
                new Product(1L, "제품1", 5000, "www.imageUrl1.com"),
                new Product(2L, "제품2", 1000, "www.image2.com"));

        Cart cart = new Cart(1L, 1L, "제품1", 5000, "www.imageUrl1.com", 50);

        given(productDao.findProducts()).willReturn(products);
        given(customerDao.findById(user.getId())).willReturn(Optional.of(customer));
        given(cartItemDao.findProductIdsByCustomerId(user.getId())).willReturn(List.of(1L));
        given(cartItemDao.findIdAndQuantityByProductId(any(), any())).willReturn(Optional.of(cart));

        List<ProductResponse> productResponses = productService.findAll(user);
        assertAll(
                () -> assertThat(productResponses.get(0).getCartId()).isEqualTo(1L),
                () -> assertThat(productResponses.get(0).getQuantity()).isEqualTo(50),
                () -> assertThat(productResponses.get(1).getCartId()).isEqualTo(null),
                () -> assertThat(productResponses.get(1).getQuantity()).isEqualTo(0)
        );
    }
}
