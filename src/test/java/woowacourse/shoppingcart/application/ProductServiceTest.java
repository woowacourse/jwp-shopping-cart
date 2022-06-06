package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:import.sql"})
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("장바구니에 담으려는 상품의 수량이 재고보다 많다면 예외를 발생시킨다.")
    @Test
    void validateStock() {
        assertThatThrownBy(() -> productService.validateStock(1L, 101))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 재고가 부족합니다.");
    }

    @DisplayName("장바구니에 담으려는 상품의 id가 존재하지 않는다면 예외를 발생시킨다.")
    @Test
    void validateProductId() {
        assertThatThrownBy(() -> productService.validateProductId(4L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }
}
