package cart.dao;

import cart.domain.ImageUrl;
import cart.domain.Item;
import cart.domain.Name;
import cart.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Sql("classpath:initializeTestDb.sql")
class ItemDaoTest {

    private final ItemDao itemDao;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Item> actorRowMapper = (resultSet, rowNumber) -> new Item.Builder()
            .id(resultSet.getLong("id"))
            .name(new Name(resultSet.getString("name")))
            .imageUrl(new ImageUrl(resultSet.getString("image_url")))
            .price(new Price(resultSet.getInt("price")))
            .build();

    @Autowired
    ItemDaoTest(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.itemDao = new ItemDao(namedParameterJdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @DisplayName("아이템의 전체 목록을 조회한다")
    @Test
    void findAll() {
        // when
        List<Item> items = itemDao.findAll();
        // then
        assertThat(items).hasSize(3);
    }

    @DisplayName("아이디를 통해 아이템을 조회한다")
    @Test
    void findBy() {
        //when
        Item findItem = itemDao.findBy(1L).orElseThrow();
        //then
        assertThat(findItem).isEqualTo(new Item.Builder()
                .id(1L)
                .name(new Name("위키드"))
                .imageUrl(new ImageUrl("https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg"))
                .price(new Price(150000))
                .build());
    }

    @DisplayName("없는 아이디를 조회하면 빈값을 반환한다")
    @Test
    void findByNotExistId() {
        //when
        Optional<Item> findItem = itemDao.findBy(100L);
        //then
        assertThat(findItem).isEmpty();
    }

    @DisplayName("아이템을 저장한다.")
    @Test
    void save() {
        // given
        Item item = new Item.Builder()
                .name(new Name("레드북"))
                .imageUrl(new ImageUrl("url"))
                .price(new Price(150000))
                .build();
        // when
        Long itemId = itemDao.save(item);
        // then
        Item findItem = namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM items WHERE id = :id",
                new MapSqlParameterSource("id", itemId),
                actorRowMapper
        );
        assertThat(findItem).isEqualTo(new Item.Builder()
                .id(itemId)
                .name(new Name("레드북"))
                .imageUrl(new ImageUrl("url"))
                .price(new Price(150000))
                .build());
    }

    @DisplayName("아이템을 수정한다")
    @Test
    void update() {
        //given
        Item editItem = new Item.Builder()
                .id(1L)
                .name(new Name("위키드2"))
                .imageUrl(new ImageUrl("수정된 URL"))
                .price(new Price(1000))
                .build();
        //when
        itemDao.update(editItem);
        //then
        Item findItem = namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM items WHERE id = :id",
                new MapSqlParameterSource("id", editItem.getId()),
                actorRowMapper
        );
        assertThat(findItem).isEqualTo(editItem);
    }

    @DisplayName("아이템을 삭제한다")
    @Test
    void delete() {
        //given
        Item targetItem = namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM items WHERE id = :id",
                new MapSqlParameterSource("id", 1L),
                actorRowMapper
        );
        //when
        itemDao.deleteBy(targetItem.getId());
        //then
        List<Item> findItems = namedParameterJdbcTemplate.query(
                "SELECT * FROM items",
                actorRowMapper
        );
        assertThat(findItems).doesNotContain(targetItem);
    }
}
