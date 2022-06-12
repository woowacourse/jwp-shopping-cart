package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import woowacourse.auth.domain.user.Customer;
import woowacourse.auth.domain.user.EncryptedPassword;
import woowacourse.common.exception.InvalidRequestException;
import woowacourse.setup.SpringBeanTest;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
class OrderServiceTest extends SpringBeanTest {

    private static final Customer 고객 = new Customer(1L, "username", new EncryptedPassword("암호화"), "닉네임", 20);
    private static final Product 호박 = new Product(1L, "호박", 1000, "호박_이미지");
    private static final Product 고구마 = new Product(2L, "고구마", 2000, "고구마_이미지");
    private static final Product 호박고구마 = new Product(3L, "호박고구마", 3000, "호박_고구마_이미지");

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private DatabaseFixture databaseFixture;

    @BeforeEach
    void setUp() {
        databaseFixture.save(호박, 고구마, 호박고구마);
    }

    @DisplayName("buyCartItems 메서드는 장바구니 항목들을 주문 데이터로 변환하고, 생성된 주문의 ID 반환")
    @Nested
    class BuyCartItemsTest {

        private final CartItem 호박3개 = new CartItem(호박, 3);
        private final CartItem 고구마3개 = new CartItem(고구마, 1);
        private final CartItem 호박고구마5개 = new CartItem(호박고구마, 5);

        @Test
        void 주문_성공시_생성된_주문의_ID_반환() {
            databaseFixture.save(고객, 호박3개, 고구마3개, 호박고구마5개);

            List<Long> 호박과_호박고구마 = List.of(호박3개.getProductId(), 호박고구마5개.getProductId());
            Long 생성된_주문_ID = orderService.buyCartItems(고객, 호박과_호박고구마);

            assertThat(생성된_주문_ID).isEqualTo(1L);
        }

        @Test
        void 주문한_장바구니_항목들은_장바구니에서_제거() {
            databaseFixture.save(고객, 호박3개, 고구마3개, 호박고구마5개);

            List<Long> 호박과_호박고구마 = List.of(호박3개.getProductId(), 호박고구마5개.getProductId());
            orderService.buyCartItems(고객, 호박과_호박고구마);
            Cart actualCart = cartService.findCart(고객);
            Cart expectedCart = new Cart(고구마3개);

            assertThat(actualCart).isEqualTo(expectedCart);
        }

        @Test
        void 장바구니에_등록되었으나_0개를_구매하려는_상품은_주문내역에서_제외() {
            CartItem 호박고구마0개 = new CartItem(호박고구마, 0);
            databaseFixture.save(고객, 호박3개, 고구마3개, 호박고구마0개);

            List<Long> 호박3개와_호박고구마0개 = List.of(호박3개.getProductId(), 호박고구마0개.getProductId());
            orderService.buyCartItems(고객, 호박3개와_호박고구마0개);
            Cart actualCart = cartService.findCart(고객);
            Cart expectedCart = new Cart(고구마3개, 호박고구마0개);

            assertThat(actualCart).isEqualTo(expectedCart);
        }

        @Test
        void 장바구니에_등록되지_않은_상품을_구매하려는_경우_예외발생() {
            long 등록되지_않은_상품_id = 99999L;

            assertThatThrownBy(() -> orderService.buyCartItems(고객, List.of(등록되지_않은_상품_id)))
                    .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        void 구매하려는_상품이_없는_경우_예외발생() {
            List<Long> emptyProductIds = List.of();

            assertThatThrownBy(() -> orderService.buyCartItems(고객, emptyProductIds))
                    .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        void 모든_상품을_0개씩만_구매하려는_경우_예외발생() {
            CartItem 호박0개 = new CartItem(호박, 0);
            CartItem 호박고구마0개 = new CartItem(호박고구마, 0);
            databaseFixture.save(고객, 호박0개, 호박고구마0개);
            List<Long> 호박0개와_호박고구마0개 = List.of(호박0개.getProductId(), 호박고구마0개.getProductId());

            assertThatThrownBy(() -> orderService.buyCartItems(고객, 호박0개와_호박고구마0개))
                    .isInstanceOf(InvalidRequestException.class);
        }
    }
}
