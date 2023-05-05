package cart.dao.cart;

import cart.dao.item.ItemDao;
import cart.dao.member.MemberCartDao;
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

import java.util.Map;
import java.util.Optional;

@SpringBootTest
@Sql({"classpath:schema.sql"})
class JdbcMemberCartDaoTest {

    @Autowired
    private MemberCartDao memberCartDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        ItemEntity item1 = itemDao.save(new ItemEntity("치킨", "a", 10000));
        ItemEntity item2 = itemDao.save(new ItemEntity("피자", "b", 20000));
        itemDao.save(item1);
        itemDao.save(item2);

        MemberEntity member = memberDao.save(new MemberEntity("test@naver.com", "test", "01012345678", "qwer1234"));

        memberCartDao.save(member.getEmail(), item1.getId());
        memberCartDao.save(member.getEmail(), item2.getId());
    }

    @Test
    @DisplayName("장바구니 저장 테스트")
    void save() {
        ItemEntity item = itemDao.save(new ItemEntity("피자1", "b", 20000));
        Long save = memberCartDao.save("test@naver.com", item.getId());
        Assertions.assertThat(save).isEqualTo(item.getId());
    }

    @Test
    @DisplayName("모든 장바구니 조회 테스트")
    void findAll() {
        Optional<Map<ItemEntity, Long>> carts = memberCartDao.findAll("test@naver.com");
        Map<ItemEntity, Long> retrievedCarts = carts.get();
        Assertions.assertThat(retrievedCarts).hasSize(2);
    }

    @Test
    @DisplayName("장바구니 삭제 테스트")
    void delete() {
        ItemEntity item = new ItemEntity(2L, "피자", "b", 20000);
        memberCartDao.delete("test@naver.com", item.getId());
        Optional<Map<ItemEntity, Long>> carts = memberCartDao.findAll("test@naver.com");
        Map<ItemEntity, Long> retrievedCarts = carts.get();
        Assertions.assertThat(retrievedCarts).hasSize(1);
    }
}