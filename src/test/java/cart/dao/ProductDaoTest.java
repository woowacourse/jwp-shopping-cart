package cart.dao;

import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;
import cart.persistence.entity.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("존재하는 상품을 조회하면, 성공적으로 가져온다.")
    @Test
    void findById_success() {
        // given
        final Long productId = productDao.insert(product);

        // when
        final Optional<Product> product = productDao.findById(productId);
        final Product findProduct = product.get();

        // then
        assertAll(() -> assertThat(findProduct.getName()).isEqualTo("치킨"),
                () -> assertThat(findProduct.getPrice()).isEqualTo(20000),
                () -> assertThat(findProduct.getImageUrl()).isEqualTo(IMAGE_URL),
                () -> assertThat(findProduct.getCategory()).isEqualTo(ProductCategory.KOREAN));
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void insert() {
        // when
        final Long productId = productDao.insert(product);

        // then
        final Optional<Product> product = productDao.findById(productId);
        final Product findProduct = product.get();
        assertAll(() -> assertThat(findProduct.getName()).isEqualTo("치킨"),
                () -> assertThat(findProduct.getPrice()).isEqualTo(20000),
                () -> assertThat(findProduct.getImageUrl()).isEqualTo(IMAGE_URL),
                () -> assertThat(findProduct.getCategory()).isEqualTo(ProductCategory.KOREAN));
    }

    @DisplayName("상품 전체를 조회한다.")
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

    @DisplayName("상품을 수정한다.")
    @Test
    void update() {
        // given
        final Long productId = productDao.insert(product);

        // when
        final Product updateProduct = new Product(productId, "탕수육", "imageUrl",
                30000, ProductCategory.CHINESE);
        int updatedCount = productDao.update(updateProduct);

        // then
        final Optional<Product> product = productDao.findById(productId);
        final Product findProduct = product.get();
        assertAll(
                () -> assertThat(updatedCount).isEqualTo(1),
                () -> assertThat(findProduct.getName()).isEqualTo("탕수육"),
                () -> assertThat(findProduct.getPrice()).isEqualTo(30000),
                () -> assertThat(findProduct.getImageUrl()).isEqualTo("imageUrl"),
                () -> assertThat(findProduct.getCategory()).isEqualTo(ProductCategory.CHINESE));
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void delete() {
        // given
        final Long productId = productDao.insert(product);

        // when
        int deletedCount = productDao.deleteById(productId);

        // then
        assertThat(deletedCount).isEqualTo(1);
    }
}
