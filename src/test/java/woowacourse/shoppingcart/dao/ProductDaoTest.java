package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.product.ProductNotFoundException;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        Product product = new Product("이름", 1000, "이미지주소");

        // when
        productDao.save(product);

        // then
        List<Product> products = productDao.findProducts();
        assertThat(products.get(products.size() - 1).getName()).isEqualTo("이름");
    }

    @DisplayName("id로 상품을 찾아 반환한다.")
    @Test
    void findById() {
        Product product = productDao.getById(1L);
        assertThat(product.getName()).isEqualTo("캐스터네츠 커스텀캣타워H_가드형");
    }

    @DisplayName("존재하지 않는 id인 경우 예외가 발생한다.")
    @Test
    void findByNotExistId() {
        assertThatThrownBy(
                () -> productDao.getById(99L)
        ).isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");
    }


    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        final int size = 0;

        // when
        final List<Product> products = productDao.findProducts();

        // then
        assertThat(products).size().isNotEqualTo(size);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(1L);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
