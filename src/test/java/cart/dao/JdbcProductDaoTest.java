package cart.dao;

import cart.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Import({JdbcProductDao.class, ProductInitializer.class})
@JdbcTest
class JdbcProductDaoTest {

    @Autowired
    JdbcProductDao productDao;

    @DisplayName("모든 상품 데이터를 반환하는지 확인한다")
    @Test
    void selectAllTest() {
        final List<ProductEntity> productEntities = productDao.selectAll();

        assertThat(productEntities.size()).isEqualTo(2);
    }

    @DisplayName("상품 등록이 되는지 확인한다")
    @Test
    void insertTest() {
        final ProductEntity productEntity = ProductEntity.of("chicken", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000);

        assertDoesNotThrow(() -> productDao.insert(productEntity));
    }

    @DisplayName("상품 수정이 되면 1을 반환하는지 확인한다")
    @Test
    void updateTest() {
        final ProductEntity productEntity = ProductEntity.of(1L, "chicken", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000);
        int updatedRow = productDao.update(productEntity);

        assertThat(updatedRow).isEqualTo(1);
    }

    @DisplayName("상품 수정이 되지 않으면 0을 반환하는지 확인한다")
    @Test
    void updateTest2() {
        final ProductEntity productEntity = ProductEntity.of(3L, "chicken", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000);
        int updatedRow = productDao.update(productEntity);

        assertThat(updatedRow).isEqualTo(0);
    }

    @DisplayName("상품 삭제가 되면 1을 반환하는지 확인한다")
    @Test
    void deleteTest() {
        final ProductEntity productEntity = ProductEntity.of(1L, null, null, null);
        int deletedRow = productDao.delete(productEntity);

        assertThat(deletedRow).isEqualTo(1);
    }

    @DisplayName("상품 삭제가 되지 않으면 0을 반환하는지 확인한다")
    @Test
    void deleteTest2() {
        final ProductEntity productEntity = ProductEntity.of(3L, null, null, null);
        int deletedRow = productDao.delete(productEntity);

        assertThat(deletedRow).isEqualTo(0);
    }
}
