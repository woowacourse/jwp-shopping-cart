package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.response.ProductResponse;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.datanotfound.ProductDataNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql"})
@DisplayName("Product 서비스 테스트")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품의 아이디를 이용하여 상품을 조회한다.")
    @Test
    void findById() {
        // given
        Long productId = productService.addProduct(new Product("name", 1_000, "url"));

        // when
        ProductResponse productResponse = productService.findById(productId);

        // then
        assertThat(productResponse).usingRecursiveComparison().isEqualTo(
                new ProductResponse(productId, "name", 1_000, "url")
        );
    }

    @DisplayName("존재하지 않는 상품을 조회하면 예외가 발생한다.")
    @Test
    void findByIdException() {
        // when & then
        assertThatThrownBy(() -> productService.findById(1L))
                .isInstanceOf(ProductDataNotFoundException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }
}
