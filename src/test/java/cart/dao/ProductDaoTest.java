package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@JdbcTest
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    void 상품을_저장한다() {
        // given
        final Product product = new Product("허브티", "tea.jpg", 1000L);

        // when
        final Optional<Long> id = productDao.saveAndGetId(product);

        // then
        final List<Product> result = productDao.findAll();
        assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(id).isPresent()
        );
    }

    @Test
    void 전체_상품을_조회한다() {
        // given
        final Product product1 = new Product("허브티", "tea.jpg", 1000L);
        final Product product2 = new Product("고양이", "cat.jpg", 1000000L);
        final Long id1 = productDao.saveAndGetId(product1).get();
        final Long id2 = productDao.saveAndGetId(product2).get();

        // when
        List<Product> result = productDao.findAll();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(List.of(
                new Product(id1, product1.getName(), product1.getImage(), product1.getPrice()),
                new Product(id2, product2.getName(), product2.getImage(), product2.getPrice())
        ));
    }
}
