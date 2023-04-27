package cart.service;

import cart.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void clear() {
        jdbcTemplate.execute("TRUNCATE TABLE product");
    }

    @Test
    void add() {
        productService.add("땡칠", "asdf", 100L);

        final List<Product> result = productService.getAll();
        assertAll(
                () -> assertThat(result.get(0).getName()).isEqualTo("땡칠"),
                () -> assertThat(result.get(0).getImage()).isEqualTo("asdf"),
                () -> assertThat(result.get(0).getPrice()).isEqualTo(100L)
        );
    }


    @Test
    void delete() {
        productService.add("땡칠", "asdf", 100L);

        productService.delete(1);

        final List<Product> result = productService.getAll();

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void update() {
        productService.add("땡칠", "asdf", 100L);
        productService.update(1, "비버", "VERY_BIG_IMAGE", 10000L);

        final List<Product> result = productService.getAll();

        assertAll(
                () -> assertThat(result.get(0).getName()).isEqualTo("비버"),
                () -> assertThat(result.get(0).getImage()).isEqualTo("VERY_BIG_IMAGE"),
                () -> assertThat(result.get(0).getPrice()).isEqualTo(10000L)
        );
    }

    @Test
    void getAll() {
        productService.add("비버", "SMALL_IMAGE", 100L);
        productService.add("땡칠", "asdf", 100L);

        final List<Product> result = productService.getAll();

        assertAll(
                () -> assertThat(result.get(0).getName()).isEqualTo("비버"),
                () -> assertThat(result.get(0).getImage()).isEqualTo("SMALL_IMAGE"),
                () -> assertThat(result.get(0).getPrice()).isEqualTo(100L),
                () -> assertThat(result.get(1).getName()).isEqualTo("땡칠"),
                () -> assertThat(result.get(1).getImage()).isEqualTo("asdf"),
                () -> assertThat(result.get(1).getPrice()).isEqualTo(100L)
        );
    }
}
