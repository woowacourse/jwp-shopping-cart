package cart.service;

import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductUpdateRequest;
import cart.controller.dto.response.ProductResponse;
import cart.dao.ProductDao;
import cart.entity.ProductEntity;
import cart.exception.CantSellNegativeQuantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    @DisplayName("상품이 성공적으로 생성된다.")
    @Test
    void create() {
        // given
        final ProductCreateRequest request = new ProductCreateRequest("베베 상품", 5000, "bebe_img");

        // when
        productService.create(request);

        // then
        verify(productDao, Mockito.atMostOnce())
                .create(request.toEntity());
    }

    @DisplayName("상품이 생성시 검증에 실패한다.")
    @Test
    void createFail() {
        //given
        final ProductCreateRequest request = new ProductCreateRequest("베베 상품", -1000, "bebe_img");

        // when, then
        Assertions.assertThrows(CantSellNegativeQuantity.class, () -> productService.create(request));
    }

    @DisplayName("상품을 전체 조회한다.")
    @Test
    void findAll() {
        // given
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(new ProductEntity(1L, "베베상품", "베베_img", 1000));
        productEntities.add(new ProductEntity(2L, "배럴상품", "베럴_img", 2000));
        Mockito.doReturn(productEntities).when(productDao).findAll();

        // when
        List<ProductResponse> responses = productService.findAll();

        // then
        Assertions.assertAll(
                () -> assertThat(responses).extracting(ProductResponse::getId)
                        .contains(1L, 2L),
                () -> assertThat(responses).extracting(ProductResponse::getName)
                        .contains("베베상품", "배럴상품"),
                () -> assertThat(responses).extracting(ProductResponse::getImageUrl)
                        .contains("베베_img", "베럴_img")
        );
    }

    @DisplayName("상품을 수정할 수 있다.")
    @Test
    void update() {
        // given
        final Long id = 1L;
        final ProductUpdateRequest request = new ProductUpdateRequest("베베 상품 가격 폭등", 6000, "bebe_img");

        // when
        productService.update(id, request);

        // then
        verify(productDao, atMostOnce()).updateById(id, request.toEntity());
    }

    @DisplayName("상품이 수정시 검증에 실패한다.")
    @Test
    void updateFail() {
        // given
        final Long id = 1L;
        final ProductUpdateRequest request = new ProductUpdateRequest("베베 상품 가격 폭등", -6000, "bebe_img");

        // when, then
        Assertions.assertThrows(CantSellNegativeQuantity.class, () -> productService.update(id, request));
    }

    @DisplayName("상품을 삭제할 수 있다.")
    @Test
    void delete() {
        // given
        final Long id = 1L;

        // when
        productService.delete(id);

        // then
        verify(productDao, atMostOnce()).deleteById(id);
    }

}
