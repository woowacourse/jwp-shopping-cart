package cart.dao;

import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Import(ProductDaoImpl.class)
class ProductDaoImplTest {

    private static final String IMAGE_URL = "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg";

    @Autowired
    private ProductDaoImpl productDao;

    private final Product productEntity = new Product("치킨", IMAGE_URL, 20000, ProductCategory.KOREAN);

    @DisplayName("존재하는 상품을 조회하면, 성공적으로 가져온다")
    @Test
    void findById_success() {
        // given
        final Long productId = productDao.insert(productEntity);

        // when
        final Product findProductEntity = productDao.findById(productId).get();

        // then
        assertAll(() -> assertThat(findProductEntity.getProductNameValue()).isEqualTo("치킨"),
                () -> assertThat(findProductEntity.getPriceValue()).isEqualTo(20000),
                () -> assertThat(findProductEntity.getImageUrlValue()).isEqualTo(IMAGE_URL),
                () -> assertThat(findProductEntity.getCategory()).isEqualTo(ProductCategory.KOREAN));
    }

    @DisplayName("존재하지 않는 상품을 조회하면 Optional을 반환한다")
    @Test
    void findById_fail() {
        final Optional<Product> productOptional = productDao.findById(1000L);
        assertThat(productOptional.isPresent()).isFalse();
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void insert() {
        // given
        final Long productId = productDao.insert(productEntity);

        // when
        final Product findProductEntity = productDao.findById(productId).get();

        // then
        assertAll(() -> assertThat(findProductEntity.getProductNameValue()).isEqualTo("치킨"),
                () -> assertThat(findProductEntity.getPriceValue()).isEqualTo(20000),
                () -> assertThat(findProductEntity.getImageUrlValue()).isEqualTo(IMAGE_URL),
                () -> assertThat(findProductEntity.getCategory()).isEqualTo(ProductCategory.KOREAN));
    }

    @DisplayName("상품을 수정한다.")
    @Test
    void update() {
        // given
        final Long productId = productDao.insert(productEntity);
        final Product updateProductEntity = new Product(productId, "탕수육", "imageUrl", 30000, ProductCategory.CHINESE);
        int updatedCount = productDao.update(productId, updateProductEntity);

        // when
        final Product findProductEntity = productDao.findById(productId).get();

        // then
        assertAll(
                () -> assertThat(updatedCount).isEqualTo(1),
                () -> assertThat(findProductEntity.getProductNameValue()).isEqualTo("탕수육"),
                () -> assertThat(findProductEntity.getPriceValue()).isEqualTo(30000),
                () -> assertThat(findProductEntity.getImageUrlValue()).isEqualTo("imageUrl"),
                () -> assertThat(findProductEntity.getCategory()).isEqualTo(ProductCategory.CHINESE));
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void delete() {
        // given
        final Long productId = productDao.insert(productEntity);

        // when
        int deletedCount = productDao.deleteById(productId);

        // then
        assertThat(deletedCount).isEqualTo(1);
    }
}
