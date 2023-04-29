package cart.dao;

import cart.entity.Product;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
class JdbcProductsDaoTest {

    @Autowired
    private ProductsDao productsDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("truncate table products");
        jdbcTemplate.execute("alter table products alter id restart with 1");

        final String name1 = "test1";
        final int price1 = 1000;
        final String image1 = "https://domain.com/image1";
        final String name2 = "test2";
        final int price2 = 2000;
        final String image2 = "https://domain.com/image2";
        final String sql = "insert into products(product_name, product_price, product_image) values (?, ?, ?)";
        jdbcTemplate.update(sql, name1, price1, image1);
        jdbcTemplate.update(sql, name2, price2, image2);
    }

    @Test
    void create() {
        // given
        final String name = "달리";
        final int price = 1000;
        final String image = "testSource";

        // when
        productsDao.create(name, price, image);

        // then
        final Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT * FROM products ORDER BY id DESC LIMIT 1");
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
        final String newImage = "https://test.com/new/image/source";
        final Product originalProduct = productsDao.findById(id);

        //when
        productsDao.update(originalProduct, newName, newPrice, newImage);

        //then
        final Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT * FROM products where id = ?", id);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actual.get("product_name")).isEqualTo(newName);
            softly.assertThat(actual.get("product_price")).isEqualTo(newPrice);
            softly.assertThat(actual.get("product_image")).isEqualTo(newImage);
            softly.assertThat(originalProduct.getName()).isEqualTo(newName);
            softly.assertThat(originalProduct.getPrice()).isEqualTo(newPrice);
            softly.assertThat(originalProduct.getImageUrl()).isEqualTo(newImage);
        });
    }

    @Test
    void delete() {
        final long id = 1L;
        productsDao.delete(id);

        assertThatThrownBy(() -> jdbcTemplate.queryForObject("SELECT product_name FROM products where id = ?", String.class, id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
