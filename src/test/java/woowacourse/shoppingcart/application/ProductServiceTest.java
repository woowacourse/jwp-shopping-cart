package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.global.exception.InvalidProductException;
import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.application.dto.ProductSaveRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private ProductSaveRequest 치킨 = new ProductSaveRequest("치킨", 20_000, "http://chicken.test.com");
    private ProductSaveRequest 피자 = new ProductSaveRequest("피자", 30_000, "http://pizza.test.com");

    @Test
    @DisplayName("상품을 저장한다.")
    void saveProduct() {
        // given
        // when
        Long productId = productService.save(치킨);

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @Test
    @DisplayName("같은 이름의 상품을 저장할 시 에러가 발생한다.")
    void saveDuplicatedProduct() {
        // given
        productService.save(치킨);

        // when & then
        assertThatThrownBy(() -> productService.save(치킨))
                .isInstanceOf(InvalidProductException.class);
    }

    @Test
    @DisplayName("전체 상품을 조회할 수 있다.")
    void showProducts() {
        // given
        productService.save(치킨);
        productService.save(피자);

        // when
        List<ProductResponse> 전체_상품_조회 = productService.findAll();

        // then
        assertThat(전체_상품_조회).usingRecursiveComparison()
                .isEqualTo(List.of(
                        new ProductResponse(1L, "치킨", 20_000, "http://chicken.test.com"),
                        new ProductResponse(2L, "피자", 30_000, "http://pizza.test.com")
                ));
    }

    @Test
    @DisplayName("상품을 ID로 조회할 수 있다.")
    void findProduct() {
        // given
        productService.save(치킨);

        // when
        ProductResponse 치킨_조회 = productService.findById(1L);

        // then
        assertThat(치킨_조회).usingRecursiveComparison()
                .isEqualTo(new ProductResponse(1L, "치킨", 20_000, "http://chicken.test.com"));
    }
}