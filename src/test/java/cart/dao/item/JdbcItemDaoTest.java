package cart.dao.item;

import cart.entity.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql({"classpath:test_init.sql"})
class JdbcItemDaoTest {

    @Autowired
    private ItemDao itemDao;

    @BeforeEach
    void setUp() {
        itemDao.save(new ItemEntity("치킨", "a", 10000));
        itemDao.save(new ItemEntity("피자", "b", 20000));
    }

    @Test
    @DisplayName("상품 저장 테스트")
    void save() {
        ItemEntity item = new ItemEntity("햄버거", "c", 2000);

        itemDao.save(item);

        List<ItemEntity> itemEntities = itemDao.findAll();

        assertAll(
                () -> assertThat(itemEntities.size()).isEqualTo(3),
                () -> assertThat(itemEntities.get(2).getId()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("상품 목록 조회 테스트")
    void findAll() {
        List<ItemEntity> itemEntities = itemDao.findAll();

        assertAll(
                () -> assertThat(itemEntities.size()).isEqualTo(2),
                () -> assertThat(itemEntities.get(1).getId()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("상품 정보 업데이트 테스트")
    void update() {
        ItemEntity item = new ItemEntity("햄버거", "c", 2000);

        itemDao.update(2L, item);

        List<ItemEntity> itemEntities = itemDao.findAll();

        assertAll(
                () -> assertThat(itemEntities.size()).isEqualTo(2),
                () -> assertThat(itemEntities.get(1).getId()).isEqualTo(2),
                () -> assertThat(itemEntities.get(1).getName()).isEqualTo("햄버거"),
                () -> assertThat(itemEntities.get(1).getImageUrl()).isEqualTo("c"),
                () -> assertThat(itemEntities.get(1).getPrice()).isEqualTo(2000)
        );
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void delete() {
        itemDao.delete(2L);

        List<ItemEntity> itemEntities = itemDao.findAll();
        assertThat(itemEntities.size()).isEqualTo(1);
    }
}
