package cart.service;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.persistence.dao.JdbcProductDao;
import cart.persistence.entity.ProductEntity;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CartServiceTest {
    @InjectMocks
    private CartService cartService;
    @Mock
    private JdbcProductDao productDao;

    @Test
    void 새_상품을_추가한다() {
        //given
        ProductRequest productRequest = new ProductRequest("jena", 90, "http://naver.com");

        //when
        cartService.createProduct(productRequest);

        //then
        verify(productDao, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void 전체_상품을_조회한다() {
        //given
        List<ProductEntity> givenProducts = List.of(new ProductEntity("jena", 90, "http://naver.com"),
                new ProductEntity("modi", 50, "http://daum.com"));
        given(productDao.findAll()).willReturn(givenProducts);

        //when
        List<ProductResponse> products = cartService.readAllProducts();

        //then
        assertThat(products).isEqualTo(givenProducts.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList()));
    }

    @Test
    void 상품_정보를_수정한다() {
        //given
        long productId = 1L;
        ProductRequest productRequest = new ProductRequest("bingbong", 100, "http://naver.com");

        //when
        cartService.updateProduct(productId, productRequest);

        //then
        verify(productDao, times(1)).update(any(ProductEntity.class));
    }

    @Test
    void 상품_정보를_삭제한다() {
        //given
        long productId = 1L;

        //when
        cartService.deleteProduct(productId);

        //then
        verify(productDao).deleteById(productId);
    }

    @Test
    void 없는_상품_ID를_삭제할때_예외를_던진다() {
        //given
        long nonExistingProductId = 999L;
        doThrow(new EmptyResultDataAccessException(0)).when(productDao).deleteById(nonExistingProductId);

        //when
        assertThrows(EmptyResultDataAccessException.class, () -> cartService.deleteProduct(nonExistingProductId));

        //then
        verify(productDao).deleteById(nonExistingProductId);
    }
}
