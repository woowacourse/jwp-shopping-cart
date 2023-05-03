package cart.dao;

import cart.entity.Product;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Sql({"classpath:truncateTable.sql","classpath:productsTestData.sql"})
class JdbcProductsDaoTest {
    private final ProductsDao productsDao;

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcProductsDaoTest(JdbcTemplate jdbcTemplate) {
        this.productsDao = new JdbcProductsDao(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    void create() {
        // given
        final String name = "달리";
        final int price = 1000;
        final String image = "https://pbs.twimg.com/profile_images/1374979417915547648/vKspl9Et_400x400.jpg";

        // when
        productsDao.create(name, price, image);

        // then
        final Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT * FROM products_table ORDER BY id DESC LIMIT 1");
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actual.get("product_name")).isEqualTo(name);
            softly.assertThat(actual.get("product_price")).isEqualTo(price);
            softly.assertThat(actual.get("product_image")).isEqualTo(image);
        });
    }

    @Test
    void readAll() {
        assertThat(productsDao.readAll()).hasSize(2);
    }

    @Test
    void update() {
        //given
        final long id = 2L;
        final String newName = "newName";
        final int newPrice = 1000;
        final String newImage = "https://pbs.twimg.com/profile_images/1374979417915547648/vKspl9Et_400x400.jpg";
        final Product updateProduct = new Product(id, newName, newPrice, newImage);

        final List<Long> selectIdFromProducts = jdbcTemplate.queryForList("SELECT id FROM products_table", Long.class);
        System.out.println(selectIdFromProducts.size());

        //when
        productsDao.update(updateProduct);

        //then
        final Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT * FROM products_table where id = ?", id);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actual.get("product_name")).isEqualTo(newName);
            softly.assertThat(actual.get("product_price")).isEqualTo(newPrice);
            softly.assertThat(actual.get("product_image")).isEqualTo(newImage);
        });
    }

    @Test
    void delete() {
        final long id = 1L;
        productsDao.delete(id);

        assertThatThrownBy(() -> jdbcTemplate.queryForObject("SELECT product_name FROM products_table where id = ?", String.class, id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
