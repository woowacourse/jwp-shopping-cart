package cart.dao;

import cart.dao.dummyData.MemberInitializer;
import cart.dao.dummyData.ProductInitializer;
import cart.domain.entity.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Import({JdbcCartItemDao.class, JdbcProductDao.class, JdbcMemberDao.class, ProductInitializer.class, MemberInitializer.class})
@JdbcTest
class JdbcCartItemDaoTest {

    @Autowired
    JdbcCartItemDao cartItemDao;

    @Nested
    @DisplayName("사용자 id로 장바구니에 담긴 상품 목록을 조회하는 selectAllByMemberId 메서드 테스트")
    class SelectAllByMemberIdTest {

        @DisplayName("상품들을 반환하는 기능이 동작하는지 확인한다")
        @Test
        void successTest() {
            final List<CartItem> cartItemEntities = cartItemDao.selectAllByMemberId(1L);

            assertThat(cartItemEntities.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("장바구니에 상품을 추가하는 insert 메서드 테스트")
    class InsertTest {

        @DisplayName("장바구니에 상품이 등록되는지 확인한다")
        @Test
        void successTest() {
            final CartItem cartItem = CartItem.of(1L, 1L);

            assertDoesNotThrow(() -> cartItemDao.insert(cartItem));
        }

        @DisplayName("존재하지 않는 사용자 id로 상품을 장바구니에 담으려고 하면 예외가 발생하는지 확인한다")
        @Test
        void failTest() {
            final CartItem cartItem = CartItem.of(5L, 1L);

            assertThatThrownBy(() -> cartItemDao.insert(cartItem))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("존재하지 않는 상품 id로 상품을 장바구니에 담으려고 하면 예외가 발생하는지 확인한다")
        @Test
        void failTest2() {
            final CartItem cartItem = CartItem.of(1L, 4L);

            assertThatThrownBy(() -> cartItemDao.insert(cartItem))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Nested
    @DisplayName("장바구니에서 상품을 삭제하는 deleteById 메서드 테스트")
    class DeleteByIdTest {

        @DisplayName("장바구니에서 상품 삭제가 되면 1을 반환하는지 확인한다")
        @Test
        void returnOneWhenDeletedTest() {
            final CartItem cartItem = CartItem.of(1L, 1L);
            cartItemDao.insert(cartItem);

            int deletedRow = cartItemDao.deleteById(1L);

            assertThat(deletedRow).isEqualTo(1);
        }

        @DisplayName("장바구니에서 상품 삭제가 되지 않으면 0을 반환하는지 확인한다")
        @Test
        void returnZeroWhenNothingHappenedTest() {
            int deletedRow = cartItemDao.deleteById(3L);

            assertThat(deletedRow).isEqualTo(0);
        }
    }
}
