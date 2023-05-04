package cart.dao;

import cart.entity.AuthMember;
import cart.entity.CreateItem;
import cart.entity.PutCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static cart.Pixture.*;

@SpringBootTest
@Sql({"classpath:test_init.sql"})
class JdbcCartDaoTest {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        itemDao.save(CREATE_ITEM1);
        itemDao.save(CREATE_ITEM2);

        memberDao.save(AUTH_MEMBER1);
        memberDao.save(AUTH_MEMBER2);
    }

    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    @Test
    void save_success() {
        cartDao.save(new PutCart(1L, 1L));
    }
}
