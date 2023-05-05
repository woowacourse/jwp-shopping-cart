package cart.service;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private ProductService productService;

    @DisplayName("Product가 정상적으로 저장되고 id 값을 반환한다.")
    @Test
    void save() {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);
        given(productDao.save(any(Product.class))).willReturn(1L);

        // when, then
        assertThat(productService.save(productRequest)).isEqualTo(1L);
    }

    @DisplayName("모든 Product를 조회하고 ProductResponse의 List 형태로 반환한다.")
    @Test
    void findAll() {
        // given
        Product product1 = Product.from(1L, "치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);
        Product product2 = Product.from(2L, "피자", "https://cdn.dominos.co.kr/admin/upload/goods/20210226_GYHC7RpD.jpg", 20000);
        given(productDao.findAll()).willReturn(List.of(product1, product2));

        // when
        List<ProductResponse> productResponses = productService.findAll();

        // then
        assertSoftly(softly -> {
            softly.assertThat(productResponses.size()).isEqualTo(2);
            softly.assertThat(productResponses.get(0))
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(product1);
        });
    }

    @DisplayName("Product가 정상적으로 업데이트되고 업데이트된 row의 개수를 반환한다.")
    @Test
    void update_success() {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);
        Product product = Product.from(1L, productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());

        given(productDao.findById(anyLong())).willReturn(Optional.of(product));
        given(productDao.update(anyLong(), any(Product.class))).willReturn(1);

        // when, then
        assertThat(productService.update(1L, productRequest)).isEqualTo(1);
    }

    @DisplayName("업데이트 시 id가 존재하지 않을 경우 IllegalArgumentException을 반환한다.")
    @Test
    void update_fail() {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);
        given(productDao.findById(1L)).willReturn(Optional.empty());

        // when, then
        assertThrows(IllegalArgumentException.class, () -> productService.update(1L, productRequest));
    }

    @DisplayName("Product가 정상적으로 삭제된다.")
    @Test
    void delete_success() {
        // given
        given(productDao.findById(1L)).willReturn(Optional.of(new Product(1L, "치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 1000)));
        willDoNothing().given(productDao).deleteById(1L);
        willDoNothing().given(cartDao).deleteByProductId(1L);

        // when, then
        assertDoesNotThrow(() -> productService.delete(1L));
    }

    @DisplayName("삭제 시 id가 존재하지 않을 경우 IllegalArgumentException을 반환한다.")
    @Test
    void delete_fail() {
        // given
        given(productDao.findById(1L)).willReturn(Optional.empty());

        // when, then
        assertThrows(IllegalArgumentException.class, () -> productService.delete(1L));
    }
}