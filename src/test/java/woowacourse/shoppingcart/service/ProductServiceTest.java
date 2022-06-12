package woowacourse.shoppingcart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.request.ProductRequestDto;
import woowacourse.shoppingcart.dto.response.ProductResponseDto;
import woowacourse.shoppingcart.exception.NotFoundProductException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:schema-reset.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    private ProductRequestDto product1 = new ProductRequestDto("product1", 10000, null, 10);
    private ProductRequestDto product2 = new ProductRequestDto("product2", 11000, null, 10);

    @Test
    @DisplayName("상품 전체 목록을 불러온다.")
    void findProducts() {
        productService.addProduct(product1);
        productService.addProduct(product2);

        final List<ProductResponseDto> actual = productService.findProducts();

        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual).extracting("name")
                .contains("product1", "product2");
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() {

        //when
        final Long addedId = productService.addProduct(product1);

        //then
        final ProductResponseDto productDto = productService.findProductById(addedId);
        assertThat(productDto.getName()).isEqualTo(product1.getName());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProductById() {

        //given
        final Long addedId = productService.addProduct(product1);

        //when
        productService.deleteProductById(addedId);

        //then
        assertThatThrownBy(() -> productService.findProductById(addedId))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessage("존재하지 않는 상품 ID입니다.");
    }

    @Test
    @DisplayName("상품들을 추가한다.")
    void addProducts() {

        //given
        final List<ProductRequestDto> productRequestDtos = List.of(
                new ProductRequestDto("product1", 10000, null, 10),
                new ProductRequestDto("product2", 11000, null, 10)
        );

        //when
        int actual = productService.addProducts(productRequestDtos);

        //then
        assertThat(actual).isEqualTo(2);
    }
}
