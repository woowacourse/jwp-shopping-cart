package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cart.product.entity.ProductEntity;
import cart.product.dao.JdbcTemplateProductDao;
import cart.product.dao.ProductDao;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class JdbcTemplateProductDaoTest {

    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        productDao = new JdbcTemplateProductDao(jdbcTemplate);
    }

    @Test
    void insertTest() {
        final ProductEntity product = productDao.insert(new ProductEntity(null, "name", 1000, "image"));

        assertSoftly(softly -> {
            softly.assertThat(product.getName()).isEqualTo("name");
            softly.assertThat(product.getPrice()).isEqualTo(1000);
            softly.assertThat(product.getImage()).isEqualTo("image");
        });
    }

    @Test
    void findByIdTest() {
        final ProductEntity product = productDao.insert(new ProductEntity(null, "name1", 1000, "image1"));
        final int productId = product.getId();

        final ProductEntity findProduct = productDao.findById(productId);

        assertThat(findProduct.getId()).isEqualTo(productId);
        assertThat(findProduct.getName()).isEqualTo("name1");
    }

    @Test
    void findByIdTest_nonExistId_fail() {
        int nonExistId = Integer.MAX_VALUE;

        assertThatThrownBy(() -> productDao.findById(nonExistId))
                .isInstanceOf(DataAccessException.class);
    }

    @Test
    void selectAllTest() {
        productDao.insert(new ProductEntity(null, "name1", 1000, "image1"));
        productDao.insert(new ProductEntity(null, "name2", 2000, "image2"));

        List<ProductEntity> products = productDao.selectAll();
        assertThat(products).hasSize(2);
    }

    @Test
    void updateTest() {
        final ProductEntity productEntity = new ProductEntity(null, "name1", 1000, "image1");
        final ProductEntity product = productDao.insert(productEntity);
        final int productId = product.getId();

        final ProductEntity updateProduct = new ProductEntity(productId, "name2", 2000, "image2");
        final int updatedRowCount = productDao.update(updateProduct);
        final ProductEntity updatedProduct = productDao.findById(productId);

        assertSoftly(softly -> {
            softly.assertThat(updatedRowCount).isEqualTo(1);
            softly.assertThat(updatedProduct.getName()).isEqualTo("name2");
            softly.assertThat(updatedProduct.getPrice()).isEqualTo(2000);
            softly.assertThat(updatedProduct.getImage()).isEqualTo("image2");
        });
    }

    @Test
    void updateTest_nonExistProduct_nothingUpdated() {
        int nonExistId = Integer.MAX_VALUE;

        final ProductEntity product = new ProductEntity(nonExistId, "name", 1000, "image");
        final int updatedRowCount = productDao.update(product);

        assertThat(updatedRowCount).isZero();
    }

    @Test
    void deleteTest() {
        final ProductEntity productEntity = new ProductEntity(null, "name1", 1000, "image1");
        final ProductEntity product = productDao.insert(productEntity);
        final int productId = product.getId();

        final int deletedRowCount = productDao.delete(productId);

        assertThat(deletedRowCount).isOne();
    }

    @Test
    void deleteTest_nonExistProduct_nothingDeleted() {
        int nonExistId = Integer.MAX_VALUE;

        final int deletedRowCount = productDao.delete(nonExistId);

        assertThat(deletedRowCount).isZero();
    }
}
