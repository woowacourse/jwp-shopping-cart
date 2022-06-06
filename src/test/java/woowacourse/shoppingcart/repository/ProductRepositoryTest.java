package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.repository.dao.ProductDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/init.sql")
class ProductRepositoryTest {

    private ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.productRepository = new ProductRepository(new ProductDao(namedParameterJdbcTemplate));
    }

    @DisplayName("id 를 이용해서 저장되어 있는 상품을 조회한다.")
    @Sql("/setProducts.sql")
    @Test
    void findById() {
        // given
        Long productId = 1L;

        // when
        Product product = productRepository.findById(productId);

        // then
        assertAll(
                () -> assertThat(product.getId()).isEqualTo(1L),
                () -> assertThat(product.getName()).isEqualTo("apple"),
                () -> assertThat(product.getPrice()).isEqualTo(1000),
                () -> assertThat(product.getImageUrl()).isEqualTo("http://mart/apple")
        );
    }

    @DisplayName("모든 상품을 조회하는 기능")
    @Sql("/setProducts.sql")
    @Test
    void findAll() {
        // given when
        List<Product> products = productRepository.findAll();

        // then
        assertAll(
                () -> assertThat(products.size())
                        .isEqualTo(3),
                () -> assertThat(products.stream().map(Product::getName))
                        .containsExactly("apple", "peach", "banana")
        );
    }
}
