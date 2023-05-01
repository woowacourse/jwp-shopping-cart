package cart.dao;

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
@Import(ProductDaoImpl.class)
class ProductEntityDaoTest {

    private static final String IMAGE_URL = "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2-1076x807.jpg";

    @Autowired
    private ProductDaoImpl productDao;

    private final ProductEntity productEntity = new ProductEntity("치킨", IMAGE_URL, 20000, ProductCategory.KOREAN);

    @DisplayName("존재하는 상품을 조회하면, 성공적으로 가져온다")
    @Test
    void findById_success() {
        // given
        final Long productId = productDao.insert(productEntity);

        // when
        final ProductEntity findProductEntity = productDao.findById(productId).get();

        // then
        assertAll(() -> assertThat(findProductEntity.getName()).isEqualTo("치킨"),
                () -> assertThat(findProductEntity.getPrice()).isEqualTo(20000),
                () -> assertThat(findProductEntity.getImageUrl()).isEqualTo(IMAGE_URL),
                () -> assertThat(findProductEntity.getCategory()).isEqualTo(ProductCategory.KOREAN));
    }

    @DisplayName("존재하지 않는 상품을 조회하면 Optional을 반환한다")
    @Test
    void findById_fail() {
        final Optional<ProductEntity> productOptional = productDao.findById(1L);
        assertThat(productOptional.isPresent()).isFalse();
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void insert() {
        // given
        final Long productId = productDao.insert(productEntity);

        // when
        final ProductEntity findProductEntity = productDao.findById(productId).get();

        // then
        assertAll(() -> assertThat(findProductEntity.getName()).isEqualTo("치킨"),
                () -> assertThat(findProductEntity.getPrice()).isEqualTo(20000),
                () -> assertThat(findProductEntity.getImageUrl()).isEqualTo(IMAGE_URL),
                () -> assertThat(findProductEntity.getCategory()).isEqualTo(ProductCategory.KOREAN));
    }

    @DisplayName("상품 전체를 조회한다.")
    @Test
    void findAll() {
        // given
        productDao.insert(productEntity);
        productDao.insert(productEntity);

        // when
        final List<ProductEntity> productEntities = productDao.findAll();

        // then
        assertThat(productEntities).hasSize(2);
    }

    @DisplayName("상품을 수정한다.")
    @Test
    void update() {
        // given
        final Long productId = productDao.insert(productEntity);
        final ProductEntity updateProductEntity = new ProductEntity(productId, "탕수육", "imageUrl", 30000, ProductCategory.CHINESE);
        int updatedCount = productDao.update(productId, updateProductEntity);

        // when
        final ProductEntity findProductEntity = productDao.findById(productId).get();

        // then
        assertAll(
                () -> assertThat(updatedCount).isEqualTo(1),
                () -> assertThat(findProductEntity.getName()).isEqualTo("탕수육"),
                () -> assertThat(findProductEntity.getPrice()).isEqualTo(30000),
                () -> assertThat(findProductEntity.getImageUrl()).isEqualTo("imageUrl"),
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
