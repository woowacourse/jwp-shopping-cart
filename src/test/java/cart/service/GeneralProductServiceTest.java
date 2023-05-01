package cart.service;

import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.repository.product.ProductRepository;
import cart.service.product.GeneralProductService;
import cart.service.request.ProductCreateRequest;
import cart.service.request.ProductUpdateRequest;
import cart.service.response.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class GeneralProductServiceTest {
    @MockBean
    ProductRepository productRepository;

    @Autowired
    GeneralProductService generalProductService;

    @DisplayName("전체 상품 조회 테스트")
    @Test
    void findAll() {
        // given
        final List<Product> products = List.of(new Product(ProductId.from(1L), "KIARA", 1000, "이미지"));

        given(productRepository.findAll()).willReturn(products);

        // when
        final List<ProductResponse> findAll = generalProductService.findAll();

        // then
        assertThat(findAll)
                .usingRecursiveComparison()
                .isEqualTo(List.of(new ProductResponse(1L, "KIARA", 1000, "이미지")));
    }

    @DisplayName("상품 저장 테스트")
    @Test
    void save() {
        // given
        given(productRepository.save(any())).willReturn(ProductId.from(1L));

        // when
        final ProductCreateRequest request = new ProductCreateRequest("KIARA", 1000, "이미지");
        final ProductId saveId = generalProductService.save(request);

        // then
        assertThat(saveId.getId()).isEqualTo(1L);
    }

    @DisplayName("상품 삭제 테스트")
    @Test
    void deleteByProductId() {
        // given
        given(productRepository.deleteByProductId(any())).willReturn(ProductId.from(1L));

        // when
        final ProductId deleteProductId = generalProductService.deleteByProductId(ProductId.from(1L));

        // then
        assertThat(deleteProductId.getId()).isEqualTo(1L);
    }

    @DisplayName("상품 갱신 후 조회 테스트")
    @Test
    void updateProduct() {
        // given
        given(productRepository.updateByProductId(any(), any())).willReturn(ProductId.from(1L));
        given(productRepository.findByProductId(any()))
                .willReturn(Optional.ofNullable(new Product(ProductId.from(1L), "hyena", 400, "이미지")));

        // when
        final ProductUpdateRequest request = new ProductUpdateRequest("hyena", 400, "이미지");
        final ProductResponse updateProduct = generalProductService.update(ProductId.from(1L), request);

        // then
        assertThat(updateProduct)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "hyena")
                .hasFieldOrPropertyWithValue("price", 400.0)
                .hasFieldOrPropertyWithValue("image", "이미지");
    }
}
