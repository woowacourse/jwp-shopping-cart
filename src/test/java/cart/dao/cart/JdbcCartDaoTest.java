package cart.dao.cart;

import cart.dao.item.ItemDao;
import cart.dao.member.MemberDao;
import cart.entity.ItemEntity;
import cart.entity.MemberEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Sql({"classpath:schema.sql"})
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
        itemDao.save(new ItemEntity("국밥", "c", 10000));

        MemberEntity member = memberDao.save(new MemberEntity("test@naver.com", "test", "01012345678", "qwer1234"));

        cartDao.save(member.getEmail(), item1.getId());
        cartDao.save(member.getEmail(), item2.getId());
    }

    @Test
    @DisplayName("장바구니 저장 테스트")
    void save() {
        Long saveItemId = cartDao.save("test@naver.com", 3L);
        Assertions.assertThat(saveItemId).isEqualTo(3L);
    }

    @Test
    @DisplayName("모든 장바구니 조회 테스트")
    void findAll() {
        Optional<List<ItemEntity>> carts = cartDao.findAll("test@naver.com");
        List<ItemEntity> retrievedCarts = carts.get();
        Assertions.assertThat(retrievedCarts).hasSize(2);
    }

    @Test
    @DisplayName("장바구니 삭제 테스트")
    void delete() {
        cartDao.delete("test@naver.com", 1L);
        Optional<List<ItemEntity>> carts = cartDao.findAll("test@naver.com");
        List<ItemEntity> retrievedCarts = carts.get();
        Assertions.assertThat(retrievedCarts).hasSize(1);
    }
}
