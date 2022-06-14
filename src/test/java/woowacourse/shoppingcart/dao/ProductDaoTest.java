package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

import javax.sql.DataSource;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @DisplayName("상품 저장")
    @Test
    void save() {
        // given
        Product product = productDao.save(new Product("초콜렛", 1000, "www.test.com"));

        // when & then
        assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(new Product(1L, "초콜렛", 1000, "www.test.com"));
    }

    @DisplayName("상품 조회")
    @Test
    void findProductById() {
        //given
        Product product = productDao.save(new Product("초콜렛", 1000, "www.test.com"));

        // then
        assertThat(productDao.findProductById(product.getId()))
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @ParameterizedTest
    @CsvSource(value = {"12,1,12", "13,2,1", "24,2,12", "25,3,1", "26,3,2"})
    @DisplayName("전체 상품 조회 - 페이징 검사")
    void getProducts(int allSize, Long page, int expectSize) {
        // given
        for (int index = 0; index < allSize; index++) {
            productDao.save(new Product("치킨" + index, 10000, "http://example.com/chicken.jpg"));
        }

        // when & then
        assertThat(productDao.findProducts((page - 1) * 12L, 12L)
                .size())
                .isEqualTo(expectSize);
    }

    @DisplayName("상품 삭제")
    @Test
    void deleteProduct() {
        //given
        Product product = productDao.save(new Product("초콜렛", 1000, "www.test.com"));

        //when
        productDao.delete(product.getId());

        // then
        assertThatThrownBy(() -> productDao.findProductById(product.getId()))
                .isInstanceOf(InvalidProductException.class)
                .hasMessageContaining("존재하지 않는 상품 아이디 입니다.");
    }
}
