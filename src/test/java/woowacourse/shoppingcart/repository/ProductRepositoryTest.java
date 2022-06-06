package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
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
    @Test
    @Sql("/setProducts.sql")
    void findById() {
        // given
        Long id = 1L;

        // when
        Product product = productRepository.findById(id);

        // then
        assertAll(
                () -> assertThat(product.getId()).isEqualTo(1L),
                () -> assertThat(product.getName()).isEqualTo("apple"),
                () -> assertThat(product.getPrice()).isEqualTo(1000),
                () -> assertThat(product.getImageUrl()).isEqualTo("http://mart/apple")
        );
    }
}
