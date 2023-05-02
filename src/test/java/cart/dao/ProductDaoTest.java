package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Product;
import cart.fixture.ProductFixture;
import cart.fixture.ProductFixture.BLACKCAT;
import cart.fixture.ProductFixture.HERB;
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
        final Product product = HERB.PRODUCT;

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
        final Product product1 = BLACKCAT.PRODUCT;
        final Product product2 = HERB.PRODUCT;
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

    @Test
    void 단일_상품을_조회한다() {
        // given
        final Product product = HERB.PRODUCT;
        final Long id = productDao.saveAndGetId(product).get();

        // when
        final Product result = productDao.findById(id).get();

        // then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(id),
                () -> assertThat(result.getName().name()).isEqualTo(product.getName().name()),
                () -> assertThat(result.getImage().imageUrl()).isEqualTo(product.getImage().imageUrl()),
                () -> assertThat(result.getPrice().price()).isEqualTo(product.getPrice().price())
        );
    }

    @Test
    void 상품을_수정한다() {
        // given
        final Product product = HERB.PRODUCT;
        final Long id = productDao.saveAndGetId(product).get();
        final Product updatedProduct = ProductFixture.getProductWithId(id, BLACKCAT.PRODUCT);

        // when
        productDao.update(updatedProduct);

        // then
        final Product result = productDao.findById(id).get();
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(id),
                () -> assertThat(result.getName().name()).isEqualTo(updatedProduct.getName().name()),
                () -> assertThat(result.getImage().imageUrl()).isEqualTo(updatedProduct.getImage().imageUrl()),
                () -> assertThat(result.getPrice().price()).isEqualTo(updatedProduct.getPrice().price())
        );
    }

    @Test
    void 상품을_삭제한다() {
        // given
        final Product product = HERB.PRODUCT;
        final Long id = productDao.saveAndGetId(product).get();

        // when
        productDao.delete(id);

        // then
        assertThat(productDao.findAll()).isEmpty();
    }
}
