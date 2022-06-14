package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.application.dto.ProductSaveRequest;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("id 값으로 상품을 조회한다.")
    void findById() {
        // given
        Long saveId = productService.save(new ProductSaveRequest("상품1", 1000, "https://www.test.com"));

        // when
        ProductResponse productResponse = productService.findById(saveId);

        // then
        assertThat(productResponse).usingRecursiveComparison()
                .isEqualTo(new ProductResponse(1L, "상품1", 1000, "https://www.test.com"));
    }

    @Test
    @DisplayName("등록된 전체 상품을 조회한다.")
    void findProducts() {
        // given
        productService.save(new ProductSaveRequest("상품1", 1000, "https://www.test1.com"));
        productService.save(new ProductSaveRequest("상품2", 2000, "https://www.test2.com"));
        productService.save(new ProductSaveRequest("상품3", 3000, "https://www.test3.com"));

        // when
        List<ProductResponse> products = productService.findAll();

        // then
        assertThat(products).hasSize(3);
    }
}
