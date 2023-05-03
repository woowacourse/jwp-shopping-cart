package cart.service;

import cart.dao.JdbcProductDao;
import cart.domain.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
@Sql(scripts = {"classpath:data.sql"})
class ProductServiceTest {

    private final JdbcTemplate jdbcTemplate;
    private final ProductService productService;

    public ProductServiceTest(@Autowired final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.productService = new ProductService(new JdbcProductDao(jdbcTemplate));
    }

    @AfterEach
    void clear() {
        jdbcTemplate.execute("TRUNCATE TABLE product");
    }

    @Test
    @DisplayName("상품을 저장한다")
    void save() {
        assertDoesNotThrow(() -> productService.save("이오", 1000, null));
    }

    @Test
    @DisplayName("상품 리스트를 조회한다")
    void findAll() {
        productService.save("이오", 1000, null);
        productService.save("애쉬", 1000, null);

        final List<Product> actual = productService.findAll();

        assertThat(actual).extracting("name")
                .containsExactly("이오", "애쉬");
    }

    @Test
    @DisplayName("상품을 갱신시 id가 유효하지 않으면 예외 발생")
    void updateInvalidId() {
        assertThatThrownBy(() -> productService.update((long) 9999, "애쉬", 1000, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품 id 입니다.");
    }

    @Test
    @DisplayName("상품을 갱신한다")
    void update() {
        final long id = productService.save("이오", 1000, null);

        productService.update(id, "애쉬", 2000, "image");

        final Product product = productService.findAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        assertAll(
                () -> assertThat(product).isNotNull(),
                () -> assertThat(product.getId()).isEqualTo(id),
                () -> assertThat(product.getName()).isEqualTo("애쉬"),
                () -> assertThat(product.getPrice()).isEqualTo(2000),
                () -> assertThat(product.getImage()).isEqualTo("image")
        );
    }

    @Test
    @DisplayName("상품을 삭제한다")
    void delete() {
        final long id = productService.save("이오", 1000, null);

        productService.delete(id);

        final Product product = productService.findAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        assertThat(product).isNull();
    }
}
