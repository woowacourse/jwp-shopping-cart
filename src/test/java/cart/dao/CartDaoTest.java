package cart.dao;

import cart.dto.entity.CartEntity;
import cart.dto.entity.MemberCartEntity;
import cart.dto.entity.MemberEntity;
import cart.dto.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

@JdbcTest
@Sql("/data.sql")
class CartDaoTest {

    private CartDao cartDao;
    private MemberDao memberDao;
    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("장바구니 상품을 추가할 수 있다.")
    void save() {
        memberDao.save(new MemberEntity("eastsea@eastsea", "eastsea"));
        productDao.save(new ProductEntity("ocean", "바다 사진", 10000));
        CartEntity cartEntity = new CartEntity(1L, 1L);

        cartDao.save(cartEntity);

        assertThat(countRowsInTableWhere(jdbcTemplate, "carts", "product_id=1L and member_id=1L"))
                .isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니 상품을 삭제할 수 있다.")
    void delete() {
        memberDao.save(new MemberEntity("eastsea@eastsea", "eastsea"));
        productDao.save(new ProductEntity("ocean", "바다 사진", 10000));
        CartEntity cartEntity = new CartEntity(1L, 1L);

        cartDao.delete(cartEntity);

        assertThat(countRowsInTableWhere(jdbcTemplate, "carts", "product_id=1L and member_id=1L"))
                .isEqualTo(0);
    }

    @Test
    @DisplayName("멤버id로 장바구니 상품을 조회할 수 있다.")
    void findCartByMember() {
        memberDao.save(new MemberEntity("eastsea@eastsea", "eastsea"));
        productDao.save(new ProductEntity("ocean", "바다 사진", 10000));
        CartEntity cartEntity = new CartEntity(1L, 1L);

        cartDao.save(cartEntity);

        List<MemberCartEntity> cartByMember = cartDao.findCartByMember(1L);

        assertThat(cartByMember.get(0).getProductName()).isEqualTo("ocean");
    }
}
