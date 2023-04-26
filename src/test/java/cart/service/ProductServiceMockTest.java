package cart.service;

import cart.domain.Product;
import cart.dto.ProductCreateRequestDto;
import cart.dto.ProductEditRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.ProductsResponseDto;
import cart.exception.ProductNotFoundException;
import cart.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
                Product.from(1L, "라면", "imgUrl", 10000),
                Product.from(2L, "김밥", "imgUrl", 10000));
        given(productRepository.findAll()).willReturn(givenProducts);

        // when
        ProductsResponseDto result = productService.findAll();

        // then
        List<Long> givenProductIds = givenProducts.stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        List<Long> resultIds = result.getProducts().stream()
                .map(ProductResponseDto::getId)
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
        ProductCreateRequestDto req = mock(ProductCreateRequestDto.class);
        given(req.getName()).willReturn("치킨");
        given(req.getPrice()).willReturn(1000);
        given(req.getImgUrl()).willReturn("url");

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

        ProductEditRequestDto req = mock(ProductEditRequestDto.class);
        given(req.getName()).willReturn("치킨 수정");
        given(req.getPrice()).willReturn(10000);
        given(req.getImgUrl()).willReturn("urlEdit");

        // when
        productService.editProduct(id, req);

        // then
        verify(productRepository).update(product);
        assertAll(
                () -> assertThat(product.getName()).isEqualTo("치킨 수정"),
                () -> assertThat(product.getPrice()).isEqualTo(10000),
                () -> assertThat(product.getImgUrl()).isEqualTo("urlEdit")
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
        Product product = Product.from(id, "치킨", "imgUrl", 10000);
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
