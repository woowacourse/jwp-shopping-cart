package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.setup.DatabaseTest;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
class CartItemDaoTest extends DatabaseTest {

    private static final Long 고객_ID = 1L;
    private static final Product 호박 = new Product(1L, "호박", 1000, "호박_이미지");
    private static final Product 고구마 = new Product(2L, "고구마", 2000, "고구마_이미지");
    private static final Product 호박고구마 = new Product(3L, "호박고구마", 3000, "호박_고구마_이미지");

    private final CartItemDao cartItemDao;
    private final DatabaseFixture databaseFixture;

    public CartItemDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        cartItemDao = new CartItemDao(jdbcTemplate);
        databaseFixture = new DatabaseFixture(jdbcTemplate);
    }

    @DisplayName("findAllByCustomerId 메서드는 customerId에 해당되는 모든 데이터를 조회하여 리스트로 반환")
    @Nested
    class FindAllByCustomerIdTest {

        private final CartItemEntity 호박3개 = new CartItemEntity(고객_ID, new CartItem(호박, 3));
        private final CartItemEntity 고구마3개 = new CartItemEntity(고객_ID, new CartItem(고구마, 3));
        private final CartItemEntity 호박고구마3개 = new CartItemEntity(고객_ID, new CartItem(호박고구마, 3));

        @Test
        void 특정_고객의_장바구니에_담긴_상품과_수량_정보들의_리스트_반환() {
            databaseFixture.save(호박, 고구마, 호박고구마);
            saveCartItems(호박3개, 고구마3개, 호박고구마3개);

            List<CartItemEntity> actual = cartItemDao.findAllByCustomerId(고객_ID);
            List<CartItemEntity> expected = List.of(호박3개, 고구마3개, 호박고구마3개);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        void 장바구니가_비어있어도_예외_미발생() {
            List<CartItemEntity> actual = cartItemDao.findAllByCustomerId(고객_ID);

            assertThat(actual).isEmpty();
        }
    }

    @DisplayName("save 메서드는 장바구니에 상품과 수량 정보 등록")
    @Nested
    class SaveTest {

        @Test
        void 아직_등록되지_않은_상품_저장_성공() {
            CartItemEntity 호박3개 = new CartItemEntity(고객_ID, new CartItem(호박, 3));
            databaseFixture.save(호박);

            cartItemDao.save(호박3개);
            boolean 저장_성공 = findCartItem(호박3개);

            assertThat(저장_성공).isTrue();
        }

        @Test
        void 이미_장바구니에_등록된_상품인_경우_예외_발생() {
            CartItemEntity 호박1개 = new CartItemEntity(고객_ID, new CartItem(호박, 1));
            CartItemEntity 호박3개 = new CartItemEntity(고객_ID, new CartItem(호박, 3));
            databaseFixture.save(호박);
            cartItemDao.save(호박1개);

            assertThatThrownBy(() -> cartItemDao.save(호박3개))
                    .isInstanceOf(DataAccessException.class);
        }
    }

    @DisplayName("updateByCustomerIdAndProductId 메서드는 이미 장바구니에 등록된 상품의 수량을 수정")
    @Nested
    class UpdateByCustomerIdAndProductIdTest {

        @Test
        void 등록된_상품의_정보_수정_성공() {
            CartItemEntity 호박3개 = new CartItemEntity(고객_ID, new CartItem(호박, 3));
            databaseFixture.save(호박);
            cartItemDao.save(호박3개);

            CartItemEntity 호박5개 = new CartItemEntity(고객_ID, new CartItem(호박, 5));
            cartItemDao.updateByCustomerIdAndProductId(호박5개);
            boolean 수정_성공 = findCartItem(호박5개);

            assertThat(수정_성공).isTrue();
        }

        @Test
        void 장바구니에_등록되지_않은_상품인_경우_예외_미발생() {
            CartItemEntity 호박3개 = new CartItemEntity(고객_ID, new CartItem(호박, 3));
            databaseFixture.save(호박);

            assertThatNoException().isThrownBy(() -> cartItemDao.save(호박3개));
        }
    }

    @DisplayName("deleteAllByCustomerId 메서드는 고객의 장바구니에 담긴 상품 정보를 전부 삭제")
    @Nested
    class DeleteAllByCustomerIdTest {

        @Test
        void customerId에_해당되는_고객의_장바구니를_완전히_비움() {
            databaseFixture.save(호박, 고구마, 호박고구마);
            saveCartItems(new CartItemEntity(고객_ID, new CartItem(호박, 3)),
                    new CartItemEntity(고객_ID, new CartItem(고구마, 3)),
                    new CartItemEntity(고객_ID, new CartItem(호박고구마, 3)));

            cartItemDao.deleteAllByCustomerId(고객_ID);
            List<CartItemEntity> 장바구니_정보 = cartItemDao.findAllByCustomerId(고객_ID);

            assertThat(장바구니_정보).isEmpty();
        }

        @Test
        void 존재하지_않는_장바구니_정보들을_제거하려는_경우_예외_미발생() {
            assertThatNoException()
                    .isThrownBy(() -> cartItemDao.deleteAllByCustomerId(고객_ID));
        }
    }

    @DisplayName("deleteByCustomerIdAndProductIds 메서드는 고객의 장바구니에 담긴 상품 정보들 중 일부를 삭제")
    @Nested
    class DeleteByCustomerIdAndProductIdsTest {

        private final CartItemEntity 호박3개 = new CartItemEntity(고객_ID, new CartItem(호박, 3));
        private final CartItemEntity 고구마3개 = new CartItemEntity(고객_ID, new CartItem(고구마, 3));
        private final CartItemEntity 호박고구마3개 = new CartItemEntity(고객_ID, new CartItem(호박고구마, 3));

        @Test
        void productIds_목록에_해당되는_상품들만_장바구니에서_제거() {
            databaseFixture.save(호박, 고구마, 호박고구마);
            saveCartItems(호박3개, 고구마3개, 호박고구마3개);
            List<Long> 호박과_호박고구마_정보  = List.of(호박3개.getProductId(), 호박고구마3개.getProductId());

            cartItemDao.deleteByCustomerIdAndProductIds(고객_ID, 호박과_호박고구마_정보 );
            List<CartItemEntity> actual = cartItemDao.findAllByCustomerId(고객_ID);
            List<CartItemEntity> expected = List.of(고구마3개);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 존재하지_않는_장바구니_정보가_포함되어도_예외_미발생하며_존재하는_정보들만_전부_제거() {
            databaseFixture.save(호박, 고구마);
            saveCartItems(호박3개, 고구마3개);
            List<Long> 호박과_호박고구마_정보  = List.of(호박3개.getProductId(), 호박고구마3개.getProductId());

            cartItemDao.deleteByCustomerIdAndProductIds(고객_ID, 호박과_호박고구마_정보 );
            List<CartItemEntity> actual = cartItemDao.findAllByCustomerId(고객_ID);
            List<CartItemEntity> expected = List.of(고구마3개);

            assertThat(actual).isEqualTo(expected);
        }
    }

    private boolean findCartItem(CartItemEntity cartItemEntity) {
        return cartItemDao.findAllByCustomerId(cartItemEntity.getCustomerId())
                .stream()
                .anyMatch(it -> it.equals(cartItemEntity));
    }

    private void saveCartItems(CartItemEntity... cartItems) {
        databaseFixture.save(List.of(cartItems));
    }
}
