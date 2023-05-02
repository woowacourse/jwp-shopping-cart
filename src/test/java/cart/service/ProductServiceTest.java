package cart.service;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("Product가 정상적으로 업데이트되고 업데이트된 row의 개수를 반환한다.")
    @Test
    void update_success() {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);
        Product product = Product.from(1L, productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());

        given(productDao.findById(anyLong())).willReturn(Optional.of(product));
        given(productDao.updateById(anyLong(), any(Product.class))).willReturn(1);

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