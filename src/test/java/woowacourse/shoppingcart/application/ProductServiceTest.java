package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.product.PageRequest;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductServiceTest {

    private final ProductService productService;

    public ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    @DisplayName("페이지별 상품 목록을 조회한다.")
    @Test
    void findByPage() {
        final PageRequest pageRequest = new PageRequest(3, 2);

        assertThat(productService.findByPage(pageRequest))
                .hasSize(2)
                .extracting(Product::getId)
                .contains(5L, 6L);
    }

    @DisplayName("페이지별 상품 목록을 조회할때 잘못된 페이지를 입력한 경우 예외가 발생한다.")
    @Test
    void findByInvalidPage() {
        final PageRequest pageRequest = new PageRequest(10, 10);

        assertThatThrownBy(() -> productService.findByPage(pageRequest))
                .isInstanceOf(EmptyResultDataAccessException.class)
                .hasMessage("잘못된 페이지입니다.");
    }
}
