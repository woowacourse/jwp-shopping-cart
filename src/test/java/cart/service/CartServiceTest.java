package cart.service;

import static cart.fixture.DomainFactory.MAC_BOOK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.domain.item.Item;
import cart.exception.cart.CartAlreadyExistsException;
import cart.exception.item.ItemNotFoundException;
import cart.exception.user.UserNotFoundException;
import cart.repository.CartRepository;
import cart.repository.ItemRepository;
import cart.repository.UserRepository;
import cart.repository.dao.CartDao;
import cart.repository.dao.CartItemDao;
import cart.repository.dao.ItemDao;
import cart.repository.dao.UserDao;
import cart.service.dto.CartDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartServiceTest {

    UserRepository userRepository;
    ItemRepository itemRepository;
    CartRepository cartRepository;
    CartService cartService;

    @BeforeEach
    void setUp(@Autowired JdbcTemplate jdbcTemplate) {
        UserDao userDao = new UserDao(jdbcTemplate);
        ItemDao itemDao = new ItemDao(jdbcTemplate);
        CartDao cartDao = new CartDao(jdbcTemplate);
        CartItemDao cartItemDao = new CartItemDao(jdbcTemplate);

        userRepository = new UserRepository(userDao);
        itemRepository = new ItemRepository(itemDao);
        cartRepository = new CartRepository(cartDao, cartItemDao);

        cartService = new CartService(userRepository, itemRepository, cartRepository);
    }

    @Nested
    @DisplayName("CRD 성공 테스트")
    class CartServiceCRDSuccessTest {

        Item item;

        @BeforeEach
        void setUp() {
            item = itemRepository.insert(new Item("a", "https://image.com", 10000));
        }

        @Test
        @DisplayName("장바구니에 상품을 추가한다.")
        void addCartSuccess() {

            CartDto cartDto = cartService.addItem("a@a.com", item.getId());

            assertThat(cartDto.getCartId()).isPositive();
        }

        @Nested
        @DisplayName("RD 성공 테스트")
        class CartServiceRDSuccessTest {

            @BeforeEach
            void setUp() {
                cartService.addItem("a@a.com", item.getId());
            }

            @Test
            @DisplayName("장바구니의 모든 상품을 찾는다.")
            void findAllSuccess() {
                CartDto actual = cartService.findCart("a@a.com");

                assertThat(actual.getItemDtos()).isNotEmpty();
            }
        }
    }

    @Test
    @DisplayName("존재하지 않는 사용자 이메일로 장바구니를 추가하면 예외가 발생한다")
    void insertCartFailWithNotExistsEmail() {
        assertThatThrownBy(() -> cartService.addItem("c@c.com", 1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 상품을 장바구니에 추가하면 예외가 발생한다")
    void insertCartFailWithNotExistsItemId() {
        assertThatThrownBy(() -> cartService.addItem("b@b.com", -100L))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("등록하고자 하는 상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("이미 장바구니에 추가한 상품을 추가하면 예외가 발생한다")
    void insertCartFailWithAlreadyExistsItem() {
        Item item = itemRepository.insert(MAC_BOOK);
        cartService.addItem("a@a.com", item.getId());

        assertThatThrownBy(() -> cartService.addItem("a@a.com", item.getId()))
                .isInstanceOf(CartAlreadyExistsException.class)
                .hasMessage("이미 장바구니에 존재하는 상품입니다.");
    }

    @Test
    @DisplayName("장바구니에 존재하지 않는 상품을 삭제하면 예외가 발생한다")
    void deleteCartFailWithNotExistsCart() {
        assertThatThrownBy(() -> cartService.deleteCartItem("a@a.com", -999L))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("장바구니에서 일치하는 상품을 찾을 수 없습니다.");
    }
}
