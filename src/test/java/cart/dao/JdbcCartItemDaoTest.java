package cart.dao;

import cart.domain.entity.CartItem;
import cart.domain.entity.Member;
import cart.domain.entity.Product;
import cart.dummydata.MemberInitializer;
import cart.dummydata.ProductInitializer;
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

    private final static Member validMember = Member.of(1L, "irene@email.com", "password1");
    private final static Product validProduct = Product.of(1L, "mouse", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 100000);
    private final static Member invalidMember = Member.of(5L, "irene12@email.com", "password12");
    private final static Product invalidProduct = Product.of(5L, "mousePiece", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 100000);

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
            final CartItem cartItem = CartItem.of(validMember, validProduct);

            assertDoesNotThrow(() -> cartItemDao.insert(cartItem));
        }

        @DisplayName("존재하지 않는 사용자 id로 상품을 장바구니에 담으려고 하면 예외가 발생하는지 확인한다")
        @Test
        void failTest() {
            final CartItem cartItem = CartItem.of(invalidMember, validProduct);

            assertThatThrownBy(() -> cartItemDao.insert(cartItem))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("존재하지 않는 상품 id로 상품을 장바구니에 담으려고 하면 예외가 발생하는지 확인한다")
        @Test
        void failTest2() {
            final CartItem cartItem = CartItem.of(validMember, invalidProduct);

            assertThatThrownBy(() -> cartItemDao.insert(cartItem))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Nested
    @DisplayName("장바구니에서 상품을 삭제하는 deleteByIdAndMemberId 메서드 테스트")
    class DeleteByIdAndMemberIdTest {

        @DisplayName("장바구니에서 상품 삭제가 되면 1을 반환하는지 확인한다")
        @Test
        void returnOneWhenDeletedTest() {
            final CartItem cartItem = CartItem.of(validMember, validProduct);
            long id = cartItemDao.insert(cartItem);

            int deletedRow = cartItemDao.deleteByIdAndMemberId(id, validMember.getId());

            assertThat(deletedRow).isEqualTo(1);
        }

        @DisplayName("장바구니에서 상품 삭제가 되지 않으면 0을 반환하는지 확인한다")
        @Test
        void returnZeroWhenNothingHappenedTest() {
            int deletedRow = cartItemDao.deleteByIdAndMemberId(3L, 1L);

            assertThat(deletedRow).isEqualTo(0);
        }
    }
}
