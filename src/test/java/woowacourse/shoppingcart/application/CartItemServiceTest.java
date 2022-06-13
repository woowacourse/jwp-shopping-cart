package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.cartitem.application.CartItemService;
import woowacourse.shoppingcart.cartitem.application.dto.AddCartItemDto;
import woowacourse.shoppingcart.cartitem.application.dto.DeleteCartItemDto;
import woowacourse.shoppingcart.cartitem.application.dto.UpdateQuantityDto;
import woowacourse.shoppingcart.cartitem.dao.CartItemDao;
import woowacourse.shoppingcart.cartitem.ui.dto.CartItemQuantityRequest;
import woowacourse.shoppingcart.cartitem.ui.dto.CartItemRequest;
import woowacourse.shoppingcart.cartitem.ui.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.cartitem.domain.CartItem;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.cartitem.ui.dto.CartItemResponse;
import woowacourse.shoppingcart.product.domain.ThumbnailImage;
import woowacourse.shoppingcart.exception.AlreadyExistException;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.NotExistException;

@SpringBootTest
@Sql(scripts = "classpath:truncate.sql")
public class CartItemServiceTest {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CustomerDao customerDao;

    private static final String EMAIL = "test@gmail.com";
    private static final String 맥북 = "맥북";
    private static final String 애플워치 = "애플워치";
    private Long 맥북Id;
    private Long 애플워치Id;
    private Long customerId;

    @BeforeEach
    void setUp() {
        customerDao.save(new Customer(EMAIL, "password1!", "이스트"));
        customerId = customerDao.findByEmail(EMAIL).getId();
        맥북Id = productDao.save(new Product("맥북", 250, 10L,
                new ThumbnailImage("test.com", "이미지입니다."))).getId();
        애플워치Id = productDao.save(new Product("애플워치", 250, 10L,
                new ThumbnailImage("test.com", "이미지입니다."))).getId();
    }

    @DisplayName("장바구니 아이템을 추가하는 기능 정상 작동")
    @Test
    void addCartItem() {
        //when
        final CartItemRequest cartItemRequest = new CartItemRequest(맥북Id, 5);
        cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest, EMAIL));
        //then
        final CartItem cartItem = cartItemDao.findByCustomerId(customerId, 맥북Id);
        assertThat(cartItem.getProduct().getId()).isEqualTo(맥북Id);
    }

    @DisplayName("이미 있는 장바구니 아이템을 추가하면 예외 발생")
    @Test
    void addExistentItem() {
        //given
        final CartItemRequest cartItemRequest = new CartItemRequest(맥북Id, 5);
        cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest, EMAIL));

        //then
        assertThatThrownBy(() -> cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest, EMAIL)))
                .isInstanceOf(AlreadyExistException.class);
    }

    @DisplayName("사용자가 담은 장바구니 아이템들을 모두 불러오는 기능")
    @Test
    void findCartItems() {
        //given
        final CartItemRequest cartItemRequest1 = new CartItemRequest(맥북Id, 5);
        final CartItemRequest cartItemRequest2 = new CartItemRequest(애플워치Id, 5);
        cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest1, EMAIL));
        cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest2, EMAIL));
        //when
        final List<CartItemResponse> cartItemResponses = cartItemService.findCartItems(EMAIL);
        final List<String> cartItemsName = cartItemResponses.stream()
                .map(CartItemResponse::getName)
                .collect(Collectors.toUnmodifiableList());
        //then
        assertThat(cartItemsName).containsExactly(맥북, 애플워치);
    }

    @DisplayName("id값으로 장바구니 아이템을 불러오는 기능")
    @Test
    void findCartItem() {
        //given
        final CartItemRequest cartItemRequest = new CartItemRequest(애플워치Id, 5);
        final Long cartItemId = cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest, EMAIL)).getId();
        //when
        final CartItemResponse cartItemResponse = cartItemService.findCartItem(EMAIL, cartItemId);
        //then
        assertThat(cartItemResponse.getName()).isEqualTo(애플워치);
    }

    @DisplayName("장바구니 아이템의 수량을 변경하는 기능")
    @Test
    void updateQuantity() {
        //given
        final Integer quantity = 7;
        final CartItemRequest cartItemRequest = new CartItemRequest(애플워치Id, 5);
        final Long cartItemId = cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest, EMAIL)).getId();
        //when
        final CartItemQuantityRequest cartItemQuantityRequest = new CartItemQuantityRequest(cartItemId, quantity);
        cartItemService.updateQuantity(UpdateQuantityDto.from(cartItemQuantityRequest, EMAIL));
        //then
        final CartItem cartItem = cartItemDao.findByCustomerId(customerId, cartItemId);
        assertThat(cartItem.getQuantity()).isEqualTo(quantity);
    }

    @DisplayName("존재하지 않는 장바구니 아이템 Id값으로 수량 업데이트를 하면 예외 발생")
    @Test
    void updateNonExistentQuantity() {
        //given
        final CartItemQuantityRequest cartItemQuantityRequest = new CartItemQuantityRequest(1L, 7);
        //then
        assertThatThrownBy(() -> cartItemService.updateQuantity(UpdateQuantityDto.from(cartItemQuantityRequest, EMAIL)))
                .isInstanceOf(NotExistException.class);
    }

    @DisplayName("장바구니 아이템 id 리스트를 받아 해당 장바구니 아이템을 삭제하는 기능")
    @Test
    void deleteCartItem() {
        //given
        final CartItemRequest cartItemRequest1 = new CartItemRequest(맥북Id, 5);
        final CartItemRequest cartItemRequest2 = new CartItemRequest(애플워치Id, 5);
        final Long cartItemId1 = cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest1, EMAIL)).getId();
        final Long cartItemId2 = cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest2, EMAIL)).getId();
        //when
        final DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest(
                List.of(cartItemId1, cartItemId2));
        cartItemService.deleteCartItem(DeleteCartItemDto.from(deleteCartItemRequest, EMAIL));
        //then
        final List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customerId);
        assertThat(cartItems).isEmpty();
    }

    @DisplayName("장바구니 아이템 id 리스트에 유효하지 않는 장바구니 아이템 id가 포함되어 있으면 예외 발생")
    @Test
    void deleteInvalidCartItem() {
        //given
        final CartItemRequest cartItemRequest1 = new CartItemRequest(맥북Id, 5);
        final CartItemRequest cartItemRequest2 = new CartItemRequest(애플워치Id, 5);
        final Long cartItemId1 = cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest1, EMAIL)).getId();
        final Long cartItemId2 = cartItemService.addCartItem(AddCartItemDto.from(cartItemRequest2, EMAIL)).getId();
        //then
        final DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest(
                List.of(cartItemId1, cartItemId2 + 1L));
        assertThatThrownBy(() -> cartItemService.deleteCartItem(DeleteCartItemDto.from(deleteCartItemRequest, EMAIL)))
                .isInstanceOf(InvalidCartItemException.class);
    }
}
