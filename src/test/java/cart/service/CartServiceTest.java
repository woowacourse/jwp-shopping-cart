package cart.service;


import static cart.fixture.MemberFixtures.INSERT_MEMBER_ENTITY;
import static cart.fixture.MemberFixtures.MEMBER_AUTH_REQUEST;
import static cart.fixture.ProductFixtures.DUMMY_SEONGHA_NAME;
import static cart.fixture.ProductFixtures.INSERT_PRODUCT_ENTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dto.CartAddRequest;
import cart.dto.CartResponse;
import cart.exception.CartProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ProductDao productDao;

    @Test
    @DisplayName("사용자 정보와 상품 ID로 장바구니에 상품을 추가한다.")
    void saveProduct() {
        // given
        long insertedProductId = productDao.insert(INSERT_PRODUCT_ENTITY);
        long insertedMemberId = memberDao.insert(INSERT_MEMBER_ENTITY);

        // when, then
        assertDoesNotThrow(() -> cartService.saveProduct(MEMBER_AUTH_REQUEST, new CartAddRequest(insertedProductId)));
    }

    @Test
    @DisplayName("사용자 정보로 장바구니 목록을 조회한다.")
    void findAllProductByMemberInfo() {
        // given
        long insertedProductId = productDao.insert(INSERT_PRODUCT_ENTITY);
        long insertedMemberId = memberDao.insert(INSERT_MEMBER_ENTITY);

        // when
        cartService.saveProduct(MEMBER_AUTH_REQUEST, new CartAddRequest(insertedProductId));
        List<CartResponse> allProductByMemberInfo = cartService.findAllProductByMemberInfo(MEMBER_AUTH_REQUEST);

        // then
        assertAll(
                () -> assertThat(allProductByMemberInfo).hasSize(1),
                () -> assertThat(allProductByMemberInfo.get(0).getName()).isEqualTo(DUMMY_SEONGHA_NAME)
        );
    }

    @Test
    @DisplayName("카트의 상품 삭제 시 멤버 ID와 상품 ID에 해당하는 상품이 없으면 예외가 발생한다.")
    void removeProductByMemberInfoAndProductId_throw_not_found() {
        // given
        memberDao.insert(INSERT_MEMBER_ENTITY);

        // when, then
        assertThatThrownBy(() -> cartService.removeByCartId(100L))
                .isInstanceOf(CartProductNotFoundException.class)
                .hasMessage("카트 ID에 해당하는 카트가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("사용자 정보와 상품 ID로 카트의 상품을 삭제한다.")
    void removeProductByMemberInfoAndProductId() {
        // given
        long insertedProductId = productDao.insert(INSERT_PRODUCT_ENTITY);
        long insertedMemberId = memberDao.insert(INSERT_MEMBER_ENTITY);
        long insertedCartid = cartService.saveProduct(MEMBER_AUTH_REQUEST, new CartAddRequest(insertedProductId));

        // when
        cartService.removeByCartId(insertedCartid);

        // then
        assertThat(cartService.findAllProductByMemberInfo(MEMBER_AUTH_REQUEST)).hasSize(0);
    }
}
