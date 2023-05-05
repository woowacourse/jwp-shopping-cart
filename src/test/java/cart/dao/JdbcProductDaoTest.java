package cart.dao;

import cart.dao.dummyData.ProductInitializer;
import cart.domain.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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


    @Nested
    @DisplayName("상품을 목록을 조회하는 selectAll 메서드 테스트")
    class SelectAllTest {

        @DisplayName("모든 상품 데이터를 반환하는지 확인한다")
        @Test
        void returnAllProductsTest() {
            final List<Product> productEntities = productDao.selectAll();

            assertThat(productEntities.size()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("id로 상품을 조회하는 selectById 메서드 테스트")
    class SelectByIdTest {

        @DisplayName("상품 데이터를 반환하는지 확인한다")
        @Test
        void returnAllProductsTest() {
            assertDoesNotThrow(() -> productDao.selectById(1L));
        }
    }

    @Nested
    @DisplayName("상품을 등록하는 insert 메서드 테스트")
    class InsertTest {

        @DisplayName("상품 등록이 되는지 확인한다")
        @Test
        void successTest() {
            final Product product = Product.of("chicken", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000);

            assertDoesNotThrow(() -> productDao.insert(product));
        }
    }

    @Nested
    @DisplayName("상품을 수정하는 update 메서드 테스트")
    class UpdateTest {

        @DisplayName("상품 수정이 되면 1을 반환하는지 확인한다")
        @Test
        void returnOneWhenUpdatedTest() {
            final Product product = Product.of(1L, "chicken", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000);
            int updatedRow = productDao.update(product);

            assertThat(updatedRow).isEqualTo(1);
        }

        @DisplayName("상품 수정이 되지 않으면 0을 반환하는지 확인한다")
        @Test
        void returnZeroWhenNothingHappenedTest() {
            final Product product = Product.of(3L, "chicken", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000);
            int updatedRow = productDao.update(product);

            assertThat(updatedRow).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("상품을 삭제하는 delete 메서드 테스트")
    class DeleteTest {

        @DisplayName("상품 삭제가 되면 1을 반환하는지 확인한다")
        @Test
        void returnOneWhenDeletedTest() {
            int deletedRow = productDao.deleteById(1L);

            assertThat(deletedRow).isEqualTo(1);
        }

        @DisplayName("상품 삭제가 되지 않으면 0을 반환하는지 확인한다")
        @Test
        void returnZeroWhenNothingHappenedTest() {
            int deletedRow = productDao.deleteById(3L);

            assertThat(deletedRow).isEqualTo(0);
        }
    }
}
