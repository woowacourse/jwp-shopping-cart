package shoppingbasket.cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import shoppingbasket.cart.entity.CartEntity;
import shoppingbasket.cart.entity.CartProductEntity;
import shoppingbasket.member.dao.JdbcTemplateMemberDao;
import shoppingbasket.member.dao.MemberDao;
import shoppingbasket.member.entity.MemberEntity;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql(scripts = "classpath:/cartTestdata.sql")
class JdbcTemplateCartDaoTest {

    @Autowired
    private DataSource dataSource;

    private CartDao cartDao;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        this.cartDao = new JdbcTemplateCartDao(dataSource);
        this.memberDao = new JdbcTemplateMemberDao(dataSource);

        final List<MemberEntity> memberEntities = memberDao.selectAll();
        for (MemberEntity memberEntity : memberEntities) {
            System.out.println("memberEntity.getId() = " + memberEntity.getId());
        }
    }

    @Test
    void insertTest() {
        int memberId = 1;
        int productId = 1;

        final CartEntity savedCart = cartDao.insert(memberId, productId);

        assertThat(savedCart.getId()).isPositive();
        assertThat(savedCart.getMemberId()).isEqualTo(memberId);
        assertThat(savedCart.getProductId()).isEqualTo(productId);
    }

    @Test
    void findByIdTest() {
        int memberId = 1;
        int productId = 1;
        final CartEntity savedCart = cartDao.insert(memberId, productId);

        final CartEntity findCart = cartDao.findById(savedCart.getId());

        assertThat(findCart).isEqualTo(savedCart);
    }

    @Test
    void findById_nonExistId_fail() {
        int nonExistId = Integer.MAX_VALUE;

        assertThatThrownBy(() -> cartDao.findById(nonExistId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void selectAllCartProductByMemberIdTest() {
        int memberId = 1;
        cartDao.insert(memberId, 1);
        cartDao.insert(memberId, 2);

        final List<CartProductEntity> cartProducts = cartDao.selectAllCartProductByMemberId(memberId);

        assertThat(cartProducts).hasSize(2);
    }

    @Test
    void deleteTest() {
        final CartEntity savedCart = cartDao.insert(1, 1);
        final Integer savedId = savedCart.getId();

        cartDao.delete(savedId);

        assertThatThrownBy(() -> cartDao.findById(savedId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void selectCartByIdTest() {
        final CartEntity savedCart = cartDao.insert(1, 1);
        final Integer savedId = savedCart.getId();

        final CartProductEntity cartProductEntity = cartDao.selectCartById(savedId);

        assertThat(cartProductEntity.getId()).isEqualTo(savedId);
    }

    @Test
    void selectCartByIdTest_nonExistId_fail() {
        int nonExistId = Integer.MAX_VALUE;

        assertThatThrownBy(() -> cartDao.selectCartById(nonExistId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
