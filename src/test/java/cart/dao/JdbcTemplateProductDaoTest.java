package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cart.entity.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
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
        int id = productDao.insert(new ProductEntity(null, "name", 1000, "image"));

        ProductEntity product = productDao.findById(id);

        assertSoftly(softly -> {
            softly.assertThat(product.getId()).isEqualTo(id);
            softly.assertThat(product.getName()).isEqualTo("name");
            softly.assertThat(product.getPrice()).isEqualTo(1000);
            softly.assertThat(product.getImage()).isEqualTo("image");
        });
    }

    @Test
    void findByIdTest() {
        final int id1 = productDao.insert(new ProductEntity(null, "name1", 1000, "image1"));
        productDao.insert(new ProductEntity(null, "name2", 2000, "image2"));

        final ProductEntity findProduct = productDao.findById(id1);

        assertThat(findProduct.getId()).isEqualTo(id1);
        assertThat(findProduct.getName()).isEqualTo("name1");
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
        final ProductEntity product = new ProductEntity(null, "name1", 1000, "image1");
        final int productId = productDao.insert(product);

        final ProductEntity updateProduct = new ProductEntity(productId, "name2", 2000, "image2");
        productDao.update(updateProduct);
        final ProductEntity updatedProduct = productDao.findById(productId);

        assertSoftly(softly -> {
            softly.assertThat(updatedProduct.getName()).isEqualTo("name2");
            softly.assertThat(updatedProduct.getPrice()).isEqualTo(2000);
            softly.assertThat(updatedProduct.getImage()).isEqualTo("image2");
        });
    }

    @Test
    void deleteTest() {
        final ProductEntity product = new ProductEntity(null, "name1", 1000, "image1");
        final int productId = productDao.insert(product);

        productDao.delete(productId);

        assertThatThrownBy(() -> productDao.findById(productId)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
