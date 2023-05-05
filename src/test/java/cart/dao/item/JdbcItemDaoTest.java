package cart.dao.item;

import cart.entity.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql({"classpath:schema.sql"})
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

        Optional<List<ItemEntity>> items = itemDao.findAll();
        List<ItemEntity> retrievedItems = items.get();

        assertAll(
                () -> assertThat(retrievedItems.size()).isEqualTo(3),
                () -> assertThat(retrievedItems.get(2).getId()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("상품 목록 조회 테스트")
    void findAll() {
        Optional<List<ItemEntity>> items = itemDao.findAll();
        List<ItemEntity> retrievedItems = items.get();

        assertAll(
                () -> assertThat(retrievedItems.size()).isEqualTo(2),
                () -> assertThat(retrievedItems.get(1).getId()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("상품 정보 업데이트 테스트")
    void update() {
        ItemEntity item = new ItemEntity("햄버거", "c", 2000);

        itemDao.update(2L, item);

        Optional<List<ItemEntity>> items = itemDao.findAll();
        List<ItemEntity> retrievedItems = items.get();

        assertAll(
                () -> assertThat(retrievedItems.size()).isEqualTo(2),
                () -> assertThat(retrievedItems.get(1).getId()).isEqualTo(2),
                () -> assertThat(retrievedItems.get(1).getName()).isEqualTo("햄버거"),
                () -> assertThat(retrievedItems.get(1).getImageUrl()).isEqualTo("c"),
                () -> assertThat(retrievedItems.get(1).getPrice()).isEqualTo(2000)
        );
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void delete() {
        itemDao.delete(2L);

        Optional<List<ItemEntity>> items = itemDao.findAll();
        List<ItemEntity> retrievedItems = items.get();
        assertThat(retrievedItems.size()).isEqualTo(1);
    }
}