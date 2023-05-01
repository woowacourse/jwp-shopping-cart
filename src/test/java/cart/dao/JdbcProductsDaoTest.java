package cart.dao;

import cart.entity.Product;
import cart.exception.NoSuchProductException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class JdbcProductsDaoTest {

    private static final String TEST_NAME_1 = "test1";
    private static final String TEST_NAME_2 = "test2";
    private static final String TEST_NAME_3 = "test3";
    private static final int TEST_PRICE_1 = 1000;
    private static final int TEST_PRICE_2 = 2000;
    private static final int TEST_PRICE_3 = 3000;
    private static final String TEST_IMAGE_URL_1 = "https://test.com/image1";
    private static final String TEST_IMAGE_URL_2 = "https://test.com/image2";
    private static final String TEST_IMAGE_URL_3 = "https://test.com/image3";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductsDao productsDao;

    @BeforeEach
    void setup() {
        productsDao = new JdbcProductsDao(jdbcTemplate);

        jdbcTemplate.execute("truncate table products;");
        jdbcTemplate.execute("alter table products alter id restart with 1;");
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void create_test() {
        // given
        final String name = "달리";
        final int price = 1000;
        final String image = "http://test.com/image/source";

        // when
        final Long id = productsDao.create(name, price, image);

        // then
        final Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT * FROM products WHERE id = ?", id);
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
        final long id = 1L;
        insertInitialData();

        // when
        final Product product = productsDao.findById(id);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(product.getName()).isEqualTo(TEST_NAME_1);
            softly.assertThat(product.getPrice()).isEqualTo(TEST_PRICE_1);
            softly.assertThat(product.getImageUrl()).isEqualTo(TEST_IMAGE_URL_1);
        });
    }

    private void insertInitialData() {
        jdbcTemplate.update("insert into products(product_name, product_price, product_image) values (?, ?, ?);",
                TEST_NAME_1, TEST_PRICE_1, TEST_IMAGE_URL_1);
        jdbcTemplate.update("insert into products(product_name, product_price, product_image) values (?, ?, ?);",
                TEST_NAME_2, TEST_PRICE_2, TEST_IMAGE_URL_2);
        jdbcTemplate.update("insert into products(product_name, product_price, product_image) values (?, ?, ?);",
                TEST_NAME_3, TEST_PRICE_3, TEST_IMAGE_URL_3);
    }

    @Test
    @DisplayName("id로 검색 실패 테스트")
    void findById_fail_test() {
        // given
        final long id = 1L;

        // when & then
        assertThatThrownBy(() -> productsDao.findById(id))
                .isInstanceOf(NoSuchProductException.class)
                .hasMessage("해당 상품이 없습니다. 입략된 상품 id : " + id);
    }

    @Test
    @DisplayName("추가된 상품 조회 테스트")
    void readAll_test() {
        // given
        insertInitialData();

        // when & then
        assertThat(productsDao.readAll()).hasSize(3);
    }

    @Test
    @DisplayName("업데이트 테스트")
    void update_test() {
        //given
        insertInitialData();
        final long id = 1L;
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
        insertInitialData();
        final long id = 1L;

        // when
        productsDao.delete(id);

        // then
        assertThatThrownBy(() -> jdbcTemplate.queryForObject("SELECT product_name FROM products where id = ?", String.class, id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
