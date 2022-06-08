package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartItemIds;
import woowacourse.shoppingcart.dto.ImageDto;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.addProductRequest;

@SpringBootTest
@Sql(scripts = "classpath:truncate.sql")
public class CartItemServiceTest {

    private CustomerService customerService;
    private CartItemService cartItemService;
    private ProductService productService;

    private Customer customer;
    private Long productId;

    @Autowired
    public CartItemServiceTest(final JdbcTemplate jdbcTemplate) {
        this.customerService = new CustomerService(new CustomerDao(jdbcTemplate));
        this.cartItemService = new CartItemService(new CartItemDao(jdbcTemplate),
                new ProductService(new ProductDao(jdbcTemplate)));
        this.productService = new ProductService(new ProductDao(jdbcTemplate));
    }

    @BeforeEach
    void setUp() {
        customer = customerService.register("test@gmail.com", "password0!", "루나");
        ProductResponse productResponse = productService.addProduct(new addProductRequest("치킨", 10_000,
                        20, new ImageDto("IMAGE_URL", "IMAGE_ALT")));
        productId = productResponse.getId();
    }

    @DisplayName("장바구니에 물품을 추가한다.")
    @Test
    void addCartItem() {
        // when
        Long actual = cartItemService.addCart(customer, productId);

        // then
        CartItem cartItem = cartItemService.findById(customer, actual);
        Long expected = cartItem.getId();
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("장바구니에 물품 수량을 변경한다.")
    @Test
    void updateQuantity() {
        // given
        Long cartItemId = cartItemService.addCart(customer, productId);

        // when
        int newQuantity = 2;
        cartItemService.updateQuantity(customer, cartItemId, newQuantity);

        // then
        CartItem cartItem = cartItemService.findById(customer, cartItemId);
        int expected = cartItem.getQuantity();
        assertThat(newQuantity).isEqualTo(expected);
    }

    @DisplayName("장바구니 물품 하나를 조회한다.")
    @Test
    void findById() {
        // given
        Long cartItemId = cartItemService.addCart(customer, productId);

        // when
        CartItem cartItem = cartItemService.findById(customer, cartItemId);

        // then
        assertThat(cartItem.getId()).isEqualTo(cartItemId);
    }

    @DisplayName("고객의 장바구니 물품을 모두 조회한다.")
    @Test
    void findCartItemsByCustomer() {
        // given
        ProductResponse response1 = productService.addProduct(new addProductRequest("콜라", 10_000,
                        20, new ImageDto("IMAGE_URL", "IMAGE_ALT")));
        ProductResponse response2 = productService.addProduct(new addProductRequest("피자", 10_000,
                        20, new ImageDto("IMAGE_URL", "IMAGE_ALT")));
        Long cartItemId1 = cartItemService.addCart(customer, productId);
        Long cartItemId2 = cartItemService.addCart(customer, response1.getId());
        Long cartItemId3 = cartItemService.addCart(customer, response2.getId());

        // when
        List<CartItem> cartItems = cartItemService.findCartItemsByCustomer(customer);

        // then
        List<Long> cartItemIds = cartItems.stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());
        assertThat(cartItemIds).contains(cartItemId1, cartItemId2, cartItemId3);
    }

    @DisplayName("장바구니 물품을 삭제한다.")
    @Test
    void deleteCart() {
        // given
        ProductResponse response1 = productService.addProduct(new addProductRequest("콜라", 10_000,
                20, new ImageDto("IMAGE_URL", "IMAGE_ALT")));
        ProductResponse response2 = productService.addProduct(new addProductRequest("피자", 10_000,
                20, new ImageDto("IMAGE_URL", "IMAGE_ALT")));
        Long cartItemId1 = cartItemService.addCart(customer, productId);
        Long cartItemId2 = cartItemService.addCart(customer, response1.getId());
        Long cartItemId3 = cartItemService.addCart(customer, response2.getId());

        // when
        cartItemService.deleteCart(customer, new CartItemIds(List.of(cartItemId1)));

        // then
        List<CartItem> cartItems = cartItemService.findCartItemsByCustomer(customer);
        List<Long> cartItemIds = cartItems.stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());
        assertThat(cartItemIds).contains(cartItemId2, cartItemId3);
    }
}
