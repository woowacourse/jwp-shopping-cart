package cart.service;

import static cart.factory.ProductFactory.createOtherProduct;
import static cart.factory.ProductFactory.createProduct;
import static cart.factory.ProductRequestDtoFactory.createProductCreateRequest;
import static cart.factory.ProductRequestDtoFactory.createProductEditRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import cart.domain.Product;
import cart.dto.ProductCreateRequest;
import cart.dto.ProductEditRequest;
import cart.dto.ProductDto;
import cart.dto.ProductsReadResponse;
import cart.exception.ProductNotFoundException;
import cart.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceMockTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("모든 상품을 조회한다.")
    void find_products_success() {
        // given
        List<Product> givenProducts = List.of(
                createProduct(),
                createOtherProduct()
        );

        given(productRepository.findAll()).willReturn(givenProducts);

        // when
        ProductsReadResponse result = productService.findAll();

        // then
        List<Long> givenProductIds = givenProducts.stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        List<Long> resultIds = result.getProducts().stream()
                .map(ProductDto::getId)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(result.getProducts().size()).isEqualTo(givenProducts.size()),
                () -> assertThat(resultIds).containsAll(givenProductIds)
        );
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void create_product_success() {
        // given
        ProductCreateRequest req = createProductCreateRequest();

        // when
        productService.createProduct(req);

        // then
        verify(productRepository).add(any(Product.class));
    }

    @Test
    @DisplayName("상품을 수정한다.")
    void edit_product_success() {
        // given
        Long id = 1L;
        Product product = Product.from(id, "치킨", "imgUrl", 1000);
        given(productRepository.findById(id)).willReturn(Optional.of(product));

        ProductEditRequest req = createProductEditRequest();
        // when
        productService.editProduct(id, req);

        // then
        verify(productRepository).update(product);
        assertAll(
                () -> assertThat(product.getName()).isEqualTo(req.getName()),
                () -> assertThat(product.getPrice()).isEqualTo(req.getPrice()),
                () -> assertThat(product.getImgUrl()).isEqualTo(req.getImgUrl())
        );
    }

    @Test
    @DisplayName("상품이 없는 경우 수정할 수 없고 예외가 발생한다.")
    void throws_exception_when_deleted_product_not_found() {
        // given
        Long id = 1L;

        // when & then
        assertThatThrownBy(() -> productService.editProduct(id, any()))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("상품을 제거한다.")
    void delete_product_success() {
        // given
        Long id = 1L;
        Product product = createProduct();
        given(productRepository.findById(id)).willReturn(Optional.of(product));

        // when
        productService.deleteById(id);

        // then
        verify(productRepository).delete(product);
    }

    @Test
    @DisplayName("상품이 없는 경우 수정할 수 없고 예외가 발생한다.")
    void throws_exception_when_edited_product_not_found() {
        // given
        Long id = 1L;

        // when & then
        assertThatThrownBy(() -> productService.deleteById(id))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
