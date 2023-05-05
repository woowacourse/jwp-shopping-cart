package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ProductJdbcDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void init() {
        productDao = new ProductJdbcDao(jdbcTemplate);
        productDao.deleteAll();
    }

    @DisplayName("상품 추가 테스트")
    @Test
    void insert() {
        // given
        ProductEntity productEntity = new ProductEntity("test", "http://www.naver.com", 1000);

        // when
        int insertProductId = productDao.insert(productEntity);

        // then
        assertThat(insertProductId).isGreaterThan(0);
    }

    @DisplayName("상품 아이디로 조회 테스트")
    @Test
    void findById() {
        // given
        ProductEntity productEntity = new ProductEntity("test", "http://www.naver.com", 1000);
        int insertProductId = productDao.insert(productEntity);

        // when
        Optional<ProductEntity> findProduct = productDao.findById(insertProductId);

        // then
        assertThat(findProduct.isEmpty()).isFalse();
        assertThat(findProduct.get().getName()).isEqualTo(productEntity.getName());
        assertThat(findProduct.get().getImageUrl()).isEqualTo(productEntity.getImageUrl());
        assertThat(findProduct.get().getPrice()).isEqualTo(productEntity.getPrice());
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void updateProduct() {
        // given
        ProductEntity productEntity = new ProductEntity("test", "http://www.naver.com", 1000);
        int insertProductId = productDao.insert(productEntity);

        ProductEntity productForUpdate = new ProductEntity(insertProductId, "update", "http://www.update.com", 2000);

        // when
        productDao.update(productForUpdate);
        Optional<ProductEntity> findProductFinishUpdate = productDao.findById(insertProductId);

        // then
        assertThat(findProductFinishUpdate.isEmpty()).isFalse();
        assertThat(findProductFinishUpdate.get().getName()).isEqualTo(productForUpdate.getName());
        assertThat(findProductFinishUpdate.get().getImageUrl()).isEqualTo(productForUpdate.getImageUrl());
        assertThat(findProductFinishUpdate.get().getPrice()).isEqualTo(productForUpdate.getPrice());
    }

    @DisplayName("상품 삭제 테스트")
    @Test
    void deleteProduct() {
        // given
        ProductEntity productEntity = new ProductEntity("test", "http://www.naver.com", 1000);
        int insertProductId = productDao.insert(productEntity);

        // when
        productDao.delete(insertProductId);
        List<ProductEntity> findProducts = productDao.findAll();

        // then
        assertThat(findProducts).hasSize(0);
    }

    @DisplayName("없는 상품 조회 테스트")
    @Test
    void findEmptyProduct() {
        // when
        Optional<ProductEntity> findProduct = productDao.findById(2);

        // then
        assertThat(findProduct.isEmpty()).isTrue();
    }

}
