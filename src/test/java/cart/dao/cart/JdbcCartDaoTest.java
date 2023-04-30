package cart.dao.cart;

import cart.dao.item.ItemDao;
import cart.dao.member.MemberDao;
import cart.entity.ItemEntity;
import cart.entity.MemberEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@Sql({"classpath:test_init.sql"})
class JdbcCartDaoTest {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        ItemEntity item1 = itemDao.save(new ItemEntity("치킨", "a", 10000));
        ItemEntity item2 = itemDao.save(new ItemEntity("피자", "b", 20000));
        ItemEntity item3 = itemDao.save(new ItemEntity("국밥", "c", 10000));
        MemberEntity member = memberDao.save(new MemberEntity("test@naver.com", "test", "01012345678", "qwer1234"));
        cartDao.save(member.getEmail(), item1.getId());
        cartDao.save(member.getEmail(), item2.getId());
    }

    @Test
    void save() {
        Long saveItemId = cartDao.save("test@naver.com", 3L);
        Assertions.assertThat(saveItemId).isEqualTo(3L);
    }

    @Test
    void findAll() {
        List<ItemEntity> carts = cartDao.findAll("test@naver.com");
        Assertions.assertThat(carts).hasSize(2);
    }

    @Test
    void delete() {
        cartDao.delete("test@naver.com", 1L);
        Assertions.assertThat(cartDao.findAll("test@naver.com")).hasSize(1);
    }
}
