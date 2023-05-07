package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.entity.CartEntity;
import cart.dto.entity.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartDao cartDao;
    private CartEntity cartEntity;
    private int productId;
    private final int memberId = 1;

    @BeforeEach
    void setUp() {
        ProductDao productDao = new ProductDao(jdbcTemplate);
        ProductEntity productEntity = new ProductEntity(
                "치킨",
                "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg",
                10000
        );
        productId = productDao.save(productEntity);

        cartDao = new CartDao(jdbcTemplate);
        cartEntity = new CartEntity(memberId, productId);
    }

    @DisplayName("사용자 아이디에 해당하는 장바구니를 조회하는 테스트")
    @Test
    void Should_Success_When_FindByMemberId() {
        for (int i = 0; i < 10; i++) {
            cartDao.save(cartEntity);
        }

        assertThat(cartDao.findByMemberId(memberId)).hasSize(10);
    }

    @DisplayName("상품 아이디에 해당하는 장바구니를 조회하는 테스트")
    @Test
    void Should_Success_When_FindByProductId() {
        for (int i = 0; i < 10; i++) {
            cartDao.save(cartEntity);
        }
        
        assertThat(cartDao.findByProductId(productId)).hasSize(10);
    }

    @DisplayName("장바구니에 상품을 저장하는 테스트")
    @Test
    void Should_Success_When_Save() {
        cartDao.save(cartEntity);
        CartEntity cart = cartDao.findByMemberId(memberId).get(0);

        assertAll(
                () -> assertThat(cart.getCount()).isEqualTo(1),
                () -> assertThat(cart.getMember_id()).isEqualTo(memberId),
                () -> assertThat(cart.getProduct_id()).isEqualTo(productId)
        );
    }

    @DisplayName("장바구니 안의 상품의 개수를 증가시키는 테스트")
    @Test
    void Should_Success_When_UpdateCount() {
        cartDao.save(cartEntity);
        cartDao.updateCount(10, productId);
        CartEntity cart = cartDao.findByMemberId(memberId).get(0);

        assertAll(
                () -> assertThat(cart.getCount()).isEqualTo(10),
                () -> assertThat(cart.getMember_id()).isEqualTo(memberId),
                () -> assertThat(cart.getProduct_id()).isEqualTo(productId)
        );
    }

    @DisplayName("장바구니 상품 삭제 테스트")
    @Test
    void Should_Success_When_Delete() {
        int id = cartDao.save(cartEntity);
        cartDao.delete(id);
        List<CartEntity> carts = cartDao.findByProductId(memberId);

        assertThat(carts).hasSize(0);
    }
}
