package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 등록 성공")
    void addProduct() {
        // given
        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");

        // then
        assertThat(productService.addProduct(productRequest))
                .usingRecursiveComparison()
                .isEqualTo(new Product(1L, "피자", 20000, "http://example.com/chicken.jpg"));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("상품 등록 실패 - 상품명 null 검증")
    void addProductNameNUllException(String name) {
        // given
        ProductRequest productRequest = new ProductRequest(name, 20000, "http://example.com/chicken.jpg");

        // then
        assertThatThrownBy(() -> productService.addProduct(productRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명과 이미지는 null 값이 올 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @DisplayName("상품 등록 실패 - 상품명 blank 검증")
    void addProductNameBlankException(String name) {
        // given
        ProductRequest productRequest = new ProductRequest(name, 20000, "http://example.com/chicken.jpg");

        // then
        assertThatThrownBy(() -> productService.addProduct(productRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명과 이미지를 입력해주세요.");
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("상품 등록 실패 - 이미지 null 검증")
    void addProductImageNUllException(String image) {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", 20000, image);

        // then
        assertThatThrownBy(() -> productService.addProduct(productRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명과 이미지는 null 값이 올 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @DisplayName("상품 등록 실패 - 이미지 blank 검증")
    void addProductImageBlankException(String image) {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", 20000, image);

        // then
        assertThatThrownBy(() -> productService.addProduct(productRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명과 이미지를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("상품 등록 실패 - 가격 양수 검증")
    void addProductPriceException(int price) {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", price, "http://example.com/chicken.jpg");

        // then
        assertThatThrownBy(() -> productService.addProduct(productRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 가격은 양수여야 합니다.");
    }

    @Test
    @DisplayName("상품 조회")
    void getProduct() {
        // given
        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductResponse product = productService.addProduct(productRequest);

        //when & then
        assertThat(productService.findProductById(product.getId()))
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @ParameterizedTest
    @CsvSource(value = {"12,1,12", "13,2,1", "24,2,12", "25,3,1", "26,3,2"})
    @DisplayName("전체 상품 조회 - 페이징 검사")
    void getProducts(int allSize, Long page, int expectSize) {
        // given
        for (int index = 0; index < allSize; index++) {
            productService.addProduct(new ProductRequest("치킨" + index, 10000, "http://example.com/chicken.jpg"));
        }

        //when & then
        assertThat(productService.findProducts(12L, page)
                .getProducts()
                .size())
                .isEqualTo(expectSize);
    }

    @Test
    @DisplayName("상품 삭제")
    void delete() {
        //given
        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductResponse product = productService.addProduct(productRequest);

        //when
        productService.deleteProductById(product.getId());

        //then
        assertThatThrownBy(() -> productService.findProductById(product.getId()))
                .isInstanceOf(InvalidProductException.class)
                .hasMessageContaining("존재하지 않는 상품 아이디 입니다.");
    }
}