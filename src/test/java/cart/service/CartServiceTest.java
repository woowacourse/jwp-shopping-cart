package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.UserDao;
import cart.domain.item.Item;
import cart.exception.cart.CartAlreadyExistsException;
import cart.exception.cart.CartNotFoundException;
import cart.exception.item.ItemNotFoundException;
import cart.exception.user.UserNotFoundException;
import cart.service.dto.CartDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    CartDao cartDao;
    ItemDao itemDao;
    UserDao userDao;
    CartService cartService;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        itemDao = new ItemDao(jdbcTemplate);
        userDao = new UserDao(jdbcTemplate);
        cartService = new CartService(cartDao, itemDao, userDao);
    }

    @Nested
    @DisplayName("CRD 성공 테스트")
    class CartServiceCRDSuccessTest {

        Item item;

        @BeforeEach
        void setUp() {
            item = itemDao.insert(new Item("a", "https://image.com", 10000));
        }

        @Test
        @DisplayName("장바구니에 상품을 추가한다.")
        void addCartSuccess() {

            CartDto cartDto = cartService.add("a@a.com", item.getId());

            assertThat(cartDto.getCartId()).isPositive();
        }

        @Nested
        @DisplayName("RD 성공 테스트")
        class CartServiceRDSuccessTest {

            private CartDto cartDto;

            @BeforeEach
            void setUp() {
                cartDto = cartService.add("a@a.com", item.getId());
            }

            @Test
            @DisplayName("장바구니의 모든 상품을 찾는다.")
            void findAllSuccess() {
                List<CartDto> cartDtos = cartService.findAllByEmail("a@a.com");

                assertThat(cartDtos).isNotEmpty();
            }

            @Test
            @DisplayName("장바구니의 상품을 삭제한다.")
            void deleteSuccess() {
                assertDoesNotThrow(() -> cartService.delete(cartDto.getCartId(), "a@a.com"));
            }
        }
    }

    @Nested
    @DisplayName("CRD 실패 테스트")
    class CartServiceFailTest {

        @Test
        @DisplayName("존재하지 않는 사용자 이메일로 장바구니를 추가하면 예외가 발생한다")
        void insertCartFailWithNotExistsEmail() {
            assertThatThrownBy(() -> cartService.add("c@c.com", 1L))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage("존재하지 않는 사용자입니다.");
        }

        @Test
        @DisplayName("존재하지 않는 상품을 장바구니에 추가하면 예외가 발생한다")
        void insertCartFailWithNotExistsItemId() {
            assertThatThrownBy(() -> cartService.add("b@b.com", -100L))
                    .isInstanceOf(ItemNotFoundException.class)
                    .hasMessage("일치하는 상품을 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("이미 장바구니에 추가한 상품을 추가하면 예외가 발생한다")
        void insertCartFailWithAlreadyExistsItem() {
            Item item = itemDao.insert(new Item("a", "https://image.com", 10000));
            cartService.add("a@a.com", item.getId());

            assertThatThrownBy(() -> cartService.add("a@a.com", item.getId()))
                    .isInstanceOf(CartAlreadyExistsException.class)
                    .hasMessage("이미 장바구니에 존재하는 상품입니다.");
        }

        @Test
        @DisplayName("장바구니에 존재하지 않는 상품을 삭제하면 예외가 발생한다")
        void deleteCartFailWithNotExistsCart() {
            assertThatThrownBy(() -> cartService.delete(-999L, "a@a.com"))
                    .isInstanceOf(CartNotFoundException.class)
                    .hasMessage("장바구니에 존재하지 않는 상품입니다.");
        }

        @ParameterizedTest(name = "사용자 이메일 {0}과 상품 ID {1}이 주어지면 예외가 발생한다")
        @DisplayName("장바구니에 삭제할 상품이 존재하지 않으면 예외가 발생한다")
        @CsvSource(value = {"5:a@a.com", "1:c@c.com"}, delimiter = ':')
        void deleteFailWithInvalidEmailOrItemId(Long cartId, String email) {
            assertThatThrownBy(() -> cartService.delete(cartId, email))
                    .isInstanceOf(CartNotFoundException.class)
                    .hasMessage("장바구니에 존재하지 않는 상품입니다.");
        }
    }
}
