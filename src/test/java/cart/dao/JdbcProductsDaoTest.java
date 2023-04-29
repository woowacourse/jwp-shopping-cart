package cart.dao;

import cart.entity.Product;
import cart.exception.NoSuchProductException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class JdbcProductsDaoTest {

    @TestConfiguration
    static class testConfig {
        @Autowired
        JdbcTemplate jdbcTemplate;

        @Bean
        public ProductsDao productsDao() {
            return new JdbcProductsDao(jdbcTemplate);
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductsDao productsDao;

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
    @Sql("classpath:testData.sql")
    void findById_success_test() {
        // given
        final long id = 1L;

        // when
        final Product product = productsDao.findById(id);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(product.getName()).isEqualTo("test1");
            softly.assertThat(product.getPrice()).isEqualTo(1000);
            softly.assertThat(product.getImageUrl()).isEqualTo("https://test.com/image1");
        });
    }

    @Test
    @DisplayName("id로 검색 실패 테스트")
    void findById_fail_test() {
        final long id = 1L;

        assertThatThrownBy(() -> productsDao.findById(id))
                .isInstanceOf(NoSuchProductException.class)
                .hasMessage("해당 상품이 없습니다. 입략된 상품 id : " + id);
    }

    @Test
    @DisplayName("추가된 상품 조회 테스트")
    @Sql("classpath:testData.sql")
    void readAll_test() {
        assertThat(productsDao.readAll()).hasSize(3);
    }

    @Test
    @DisplayName("업데이트 테스트")
    @Sql("classpath:testData.sql")
    void update_test() {
        //given
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
    @Sql("classpath:testData.sql")
    void delete_test() {
        final long id = 1L;
        productsDao.delete(id);

        assertThatThrownBy(() -> jdbcTemplate.queryForObject("SELECT product_name FROM products where id = ?", String.class, id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
