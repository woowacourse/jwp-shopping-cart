package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.ShoppingCartFixture.제품_추가_요청1;
import static woowacourse.ShoppingCartFixture.제품_추가_요청2;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.dto.response.ProductsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@Sql("/truncate.sql")
class ProductServiceTest {
    @Autowired
    private ProductService productService;


    @DisplayName("제품을 추가한다.")
    @Test
    void addProduct() {
        // given // when
        final Long 제품추가됨 = productService.addProduct(제품_추가_요청1);

        // then
        assertThat(제품추가됨).isEqualTo(1L);
    }

    @DisplayName("제품 목록을 조회한다.")
    @Test
    void findProducts() {
        productService.addProduct(제품_추가_요청1);
        productService.addProduct(제품_추가_요청2);

        //when
        final ProductsResponse 제품_목록_조회 = productService.findProducts(1, 10);
        final List<ProductResponse> 제품_목록_상품들 = 제품_목록_조회.getProducts();

        //then
        assertThat(제품_목록_조회.getTotalCount()).isEqualTo(2);
    }

    @DisplayName("단일 제품을 조회한다.")
    @Test
    void findProductById() {
        //given
        final Long 제품_아이디 = productService.addProduct(제품_추가_요청1);

        //when
        final ProductResponse 제품_단일_조회_응답 = productService.findProductById(제품_아이디);

        //then
        assertThat(제품_단일_조회_응답.getId()).isEqualTo(제품_아이디);
        assertThat(제품_단일_조회_응답.getName()).isEqualTo(제품_추가_요청1.getName());
        assertThat(제품_단일_조회_응답.getPrice()).isEqualTo(제품_추가_요청1.getPrice());
        assertThat(제품_단일_조회_응답.getImageUrl()).isEqualTo(제품_추가_요청1.getImageUrl());
    }

    @DisplayName("제품을 삭제할 수 있다.")
    @Test
    void deleteProductById() {
        //given
        final Long 제품_아이디 = productService.addProduct(제품_추가_요청1);

        //when then
        productService.deleteProductById(제품_아이디);
        assertThatThrownBy(() -> productService.findProductById(제품_아이디))
                .isInstanceOf(InvalidProductException.class);
    }

}
