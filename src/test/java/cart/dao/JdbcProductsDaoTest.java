package cart.dao;

import cart.entity.Product;
import cart.exception.NoSuchProductException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class JdbcProductsDaoTest {

    private static final String TEST_NAME = "inserted product";
    private static final int TEST_PRICE = 100000;
    private static final String TEST_IMAGE_URL = "https://test.com/inserted/image";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final ProductsDao productsDao;

    @Autowired
    public JdbcProductsDaoTest(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
        this.productsDao = new JdbcProductsDao(jdbcTemplate);
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void create_test() {
        // given
        final String name = "달리";
        final int price = 1000;
        final String image = "http://test.com/image/source";

        // when
        final Long createdId = productsDao.create(name, price, image);

        // then
        final Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT * FROM products WHERE id = ?", createdId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actual.get("product_name")).isEqualTo(name);
            softly.assertThat(actual.get("product_price")).isEqualTo(price);
            softly.assertThat(actual.get("product_image")).isEqualTo(image);
        });
    }

    @Test
    @DisplayName("id로 검색 성공 테스트")
    void findById_success_test() {
        // given
        final long insertedId = insertTestData();

        // when
        final Product product = productsDao.findById(insertedId);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(product.getName()).isEqualTo(TEST_NAME);
            softly.assertThat(product.getPrice()).isEqualTo(TEST_PRICE);
            softly.assertThat(product.getImageUrl()).isEqualTo(TEST_IMAGE_URL);
        });
    }

    private long insertTestData() {
        final MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("product_name", TEST_NAME)
                .addValue("product_price", TEST_PRICE)
                .addValue("product_image", TEST_IMAGE_URL);

        return simpleJdbcInsert.executeAndReturnKey(source).longValue();
    }

    @Test
    @DisplayName("id로 검색 실패 테스트")
    void findById_fail_test() {
        // given
        final long id = findLastInsertedId() + 1;

        // when & then
        assertThatThrownBy(() -> productsDao.findById(id))
                .isInstanceOf(NoSuchProductException.class)
                .hasMessage("해당 상품이 없습니다. 입략된 상품 id : " + id);
    }

    private long findLastInsertedId() {
        return jdbcTemplate.queryForObject("SELECT max(id) FROM products", Long.class);
    }

    @Test
    @DisplayName("전체 상품 조회 테스트")
    void readAll_test() {
        // when
        final List<Product> products = productsDao.readAll();

        // then
        assertThat(products).hasSize(3);
    }

    @Test
    @DisplayName("업데이트 테스트")
    void update_test() {
        //given
        final long id = insertTestData();
        final String newName = "newName";
        final int newPrice = 10000;
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
    @DisplayName("데이터 제거 테스트")
    void delete_test() {
        // given
        final long id = insertTestData();

        // when
        productsDao.delete(id);

        // then
        assertThatThrownBy(() -> jdbcTemplate.queryForObject("SELECT product_name FROM products where id = ?", String.class, id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
