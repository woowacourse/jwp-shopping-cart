package cart.dao;

import cart.exception.GlobalException;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;
import cart.persistence.entity.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Import(ProductDao.class)
class ProductDaoTest {

    private static final String IMAGE_URL = "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg";

    private Product product;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        product = new Product("치킨",
                IMAGE_URL,
                20000, ProductCategory.KOREAN);
    }

    @Test
    void findById_success() {
        // given
        final Long productId = productDao.insert(product);

        // when
        final Product findProduct = productDao.findById(productId);

        // then
        assertAll(() -> assertThat(findProduct.getName()).isEqualTo("치킨"),
                () -> assertThat(findProduct.getPrice()).isEqualTo(20000),
                () -> assertThat(findProduct.getImageUrl()).isEqualTo(IMAGE_URL),
                () -> assertThat(findProduct.getCategory()).isEqualTo(ProductCategory.KOREAN));
    }

    @Test
    void findById_fail() {
        assertThatThrownBy(() -> productDao.findById(1L))
                .isInstanceOf(GlobalException.class);
    }

    @Test
    void insert() {
        // when
        productDao.insert(product);

        // then
        final Product findProduct = productDao.findById(1L);
        assertThat(findProduct.getId()).isEqualTo(1L);
    }

    @Test
    void findAll() {
        // given
        productDao.insert(product);
        productDao.insert(product);

        // when
        final List<Product> products = productDao.findAll();

        // then
        assertThat(products)
                .hasSize(2);
    }
}
