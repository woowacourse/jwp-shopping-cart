package shoppingbasket.product.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.springframework.dao.EmptyResultDataAccessException;
import shoppingbasket.product.entity.ProductEntity;
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
        final List<ProductEntity> originalProducts = productDao.selectAll();
        productDao.insert(new ProductEntity("name1", 1000, "image1"));
        productDao.insert(new ProductEntity("name2", 2000, "image2"));

        List<ProductEntity> nowProducts = productDao.selectAll();
        final int nowSize = nowProducts.size();
        final int originalSize = originalProducts.size();

        assertThat(nowSize - originalSize).isEqualTo(2);
    }

    @Test
    void updateTest() {
        final ProductEntity productEntity = new ProductEntity(null, "name1", 1000, "image1");
        final ProductEntity product = productDao.insert(productEntity);
        final int productId = product.getId();

        final ProductEntity originalProduct = new ProductEntity(productId, "name2", 2000, "image2");
        final ProductEntity updatedProduct = productDao.update(originalProduct);

        assertSoftly(softly -> {
            softly.assertThat(updatedProduct.getId()).isEqualTo(productId);
            softly.assertThat(updatedProduct.getName()).isEqualTo("name2");
            softly.assertThat(updatedProduct.getPrice()).isEqualTo(2000);
            softly.assertThat(updatedProduct.getImage()).isEqualTo("image2");
        });
    }

    @Test
    void updateTest_nonExistProduct_nothingUpdated() {
        int nonExistId = Integer.MAX_VALUE;

        final ProductEntity product = new ProductEntity(nonExistId, "name", 1000, "image");

        assertThatThrownBy(() -> productDao.update(product))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void deleteTest() {
        final ProductEntity productEntity = new ProductEntity(null, "name1", 1000, "image1");
        final ProductEntity product = productDao.insert(productEntity);
        final int productId = product.getId();

        productDao.delete(productId);

        assertThatThrownBy(() -> productDao.findById(productId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void deleteTest_nonExistProduct_nothingDeleted() {
        int nonExistId = Integer.MAX_VALUE;

        final int deletedRowCount = productDao.delete(nonExistId);

        assertThat(deletedRowCount).isZero();
    }
}
