package cart.dao;

import cart.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static cart.fixture.ProductFixture.FIRST_PRODUCT;
import static cart.fixture.ProductFixture.SECOND_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
class ProductDaoTest {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        namedParameterJdbcTemplate.getJdbcTemplate().execute("ALTER TABLE PRODUCT ALTER COLUMN id RESTART WITH 1");
        productDao = new ProductDao(namedParameterJdbcTemplate);
        productDao.deleteAll();
    }

    @Test
    void 모든_상품_목록을_조회한다() {
        productDao.save(FIRST_PRODUCT.PRODUCT);
        productDao.save(SECOND_PRODUCT.PRODUCT);

        assertThat(productDao.findAll()).containsExactly(FIRST_PRODUCT.PRODUCT_WITH_ID, SECOND_PRODUCT.PRODUCT_WITH_ID);
    }

    @Test
    void 상품을_저장한다() {
        assertThat(productDao.save(FIRST_PRODUCT.PRODUCT)).isEqualTo(1L);
    }

    @Test
    void 상품을_수정한다() {
        final long id = productDao.save(FIRST_PRODUCT.PRODUCT);
        final Product updateProduct = new Product(id, "updatedPrduct", "updatedImageURl", 20_000_000);
        productDao.update(updateProduct);

        final Product actualProduct = productDao.findById(id);
        assertThat(actualProduct).isEqualTo(updateProduct);
    }

    @Test
    void 상품을_삭제한다() {
        final long id = productDao.save(FIRST_PRODUCT.PRODUCT);
        productDao.delete(id);

        assertThatThrownBy(() -> productDao.findById(id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
