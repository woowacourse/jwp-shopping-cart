package cart.service;


import static cart.fixture.MemberFixtures.INSERT_MEMBER_ENTITY;
import static cart.fixture.MemberFixtures.MEMBER_AUTH_REQUEST;
import static cart.fixture.ProductFixtures.DUMMY_SEONGHA_ID;
import static cart.fixture.ProductFixtures.INSERT_PRODUCT_ENTITY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.exception.MemberNotFoundException;
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
    @DisplayName("장바구니 상품 추가 시 사용자 정보로 조회한 사용자가 없으면 예외가 발생한다.")
    void saveProduct_throw_not_found_member() {
        // when, then
        assertThatThrownBy(() -> cartService.saveProduct(MEMBER_AUTH_REQUEST, DUMMY_SEONGHA_ID))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("사용자 인증 정보에 해당하는 사용자가 존재하지 않습니다.");
    }


    // TODO : 장바구니 목록 조회 기능 추가 시 검증 추가하기
    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void saveProduct() {
        // given
        productDao.insert(INSERT_PRODUCT_ENTITY);
        memberDao.insert(INSERT_MEMBER_ENTITY);

        // when, then
        assertDoesNotThrow(() -> cartService.saveProduct(MEMBER_AUTH_REQUEST, DUMMY_SEONGHA_ID));
    }
}
