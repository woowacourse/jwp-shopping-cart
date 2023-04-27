package cart.dao;

import cart.entity.ProductEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Import(JdbcProductDao.class)
@JdbcTest
class JdbcProductDaoTest {

    @Autowired
    JdbcProductDao productDao;

    @DisplayName("모든 상품 데이터를 반환하는지 확인한다")
    @Test
    void selectAllTest() {
        final List<ProductEntity> productEntities = productDao.selectAll();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(productEntities.size()).isEqualTo(2);
            softAssertions.assertThat(productEntities.get(0).getId()).isEqualTo(1L);
            softAssertions.assertThat(productEntities.get(0).getName()).isEqualTo("mouse");
            softAssertions.assertThat(productEntities.get(0).getImage()).isEqualTo("https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg");
            softAssertions.assertThat(productEntities.get(0).getPrice()).isEqualTo(100_000);
            softAssertions.assertThat(productEntities.get(1).getId()).isEqualTo(2L);
            softAssertions.assertThat(productEntities.get(1).getName()).isEqualTo("keyboard");
            softAssertions.assertThat(productEntities.get(1).getImage()).isEqualTo("https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1");
            softAssertions.assertThat(productEntities.get(1).getPrice()).isEqualTo(250_000);
        });
    }

    @DisplayName("상품 등록이 되는지 확인한다")
    @Test
    void insertTest() {
        final ProductEntity productEntity = ProductEntity.of("chicken", "image", 10000);

        assertDoesNotThrow(() -> productDao.insert(productEntity));
    }

    @DisplayName("상품 수정이 되는지 확인한다")
    @Test
    void updateTest() {
        final Long id = 1L;
        final ProductEntity productEntity = ProductEntity.of(1L, "chicken", "image", 10000);

        assertDoesNotThrow(() -> productDao.updateById(id, productEntity));
    }

    @DisplayName("상품 삭제가 되는지 확인한다")
    @Test
    void deleteTest() {
        final Long id = 1L;

        assertDoesNotThrow(() -> productDao.deleteById(id));
    }
}
