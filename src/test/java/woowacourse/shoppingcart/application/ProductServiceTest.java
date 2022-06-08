package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static woowacourse.shoppingcart.application.ProductFixture.바나나;
import static woowacourse.shoppingcart.application.ProductFixture.사과;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

class ProductServiceTest {

    private final ProductService productService;

    @Mock
    private ProductDao productDao;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.productService = new ProductService(productDao);
    }

    @Test
    @DisplayName("상품을 생성한다.")
    void addProduct() {
        //given
        given(productDao.save(any(Product.class))).willReturn(바나나);
        //when
        ProductRequest productRequest = new ProductRequest("바나나", 1_000, "banana.com");
        ProductResponse productResponse = productService.addProduct(productRequest);
        //then
        assertAll(
                () -> assertThat(productResponse.getId()).isEqualTo(1L),
                () -> assertThat(productResponse.getName()).isEqualTo("바나나"),
                () -> assertThat(productResponse.getPrice()).isEqualTo(1_000),
                () -> assertThat(productResponse.getImageUrl()).isEqualTo("banana.com")
        );
    }

    @Test
    @DisplayName("상품의 가격이 음수일 경우 예외를 반환한다.")
    void throwExceptionWhenNegativePrice() {
        //given
        //when
        ProductRequest productRequest = new ProductRequest("바나나우유", -1, "banana-milk.com");
        //then
        assertThatThrownBy(() -> productService.addProduct(productRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 음수가 될 수 없습니다.");
    }

    @Test
    @DisplayName("상품 목록을 조회한다.")
    void findProducts() {
        //given
        given(productDao.findProducts()).willReturn(List.of(바나나, 사과));
        //when

        ProductsResponse products = productService.findProducts();
        //then
        assertAll(
                () -> assertThat(products.getProducts().size()).isEqualTo(2),
                () -> {
                    ProductResponse product = products.getProducts().get(0);
                    assertAll(
                            () -> assertThat(product.getId()).isEqualTo(1L),
                            () -> assertThat(product.getName()).isEqualTo("바나나"),
                            () -> assertThat(product.getPrice()).isEqualTo(1_000),
                            () -> assertThat(product.getImageUrl()).isEqualTo("banana.com")
                    );
                }
        );

    }

    @Test
    @DisplayName("상품 ID에 따른 상품을 찾는다.")
    void findProductById() {
        //given
        given(productDao.findProductById(1L)).willReturn(Optional.of(바나나));
        //when

        //then
        ProductResponse productResponse = productService.findProductById(1L);
        assertAll(
                () -> assertThat(productResponse.getId()).isEqualTo(1L),
                () -> assertThat(productResponse.getName()).isEqualTo("바나나"),
                () -> assertThat(productResponse.getPrice()).isEqualTo(1_000),
                () -> assertThat(productResponse.getImageUrl()).isEqualTo("banana.com")
        );
    }

    @Test
    @DisplayName("찾는 상품의 아이디가 없으면 예외를 반환한다.")
    void notExistProduct() {
        //given
        given(productDao.findProductById(any(Long.class))).willReturn(Optional.empty());
        //when

        //then
        assertThatThrownBy(() -> productService.findProductById(100L))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("올바르지 않은 상품 아이디 입니다.");
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProductById() {
        //given
        given(productDao.delete(any(Long.class))).willReturn(1);
        //when
        int affectedQuery = productService.deleteProductById(1L);
        //then
        assertThat(affectedQuery).isEqualTo(1);
    }
}
