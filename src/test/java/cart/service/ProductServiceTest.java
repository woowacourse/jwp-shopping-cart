package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cart.controller.dto.request.product.ProductInsertRequest;
import cart.controller.dto.request.product.ProductUpdateRequest;
import cart.controller.dto.response.ProductResponse;
import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @Mock
    ProductDao productDao;

    @InjectMocks
    ProductService productService;

    @DisplayName("상품 추가 테스트")
    @Test
    void insert() {
        String name = "테스트상품";
        String url = "http://www.naver.com";
        int price = 1000;

        ProductInsertRequest 추가할테스트상품요청 = new ProductInsertRequest(name, url, price);
        int 추가된상품아이디 = 1;

        when(productDao.insert(any(ProductEntity.class)))
                .thenReturn(추가된상품아이디);

        Integer createdId = productService.insert(추가할테스트상품요청);

        verify(productDao, atLeastOnce()).insert(any(ProductEntity.class));
        assertThat(createdId).isEqualTo(추가된상품아이디);
    }

    @DisplayName("없는 상품 수정 시 예외 테스트")
    @Test
    void updateFailByNonExistProductId() {
        String name = "테스트상품";
        String url = "http://www.naver.com";
        int price = 1000;

        ProductUpdateRequest 수정할테스트상품요청 = new ProductUpdateRequest(name, url, price);
        int 없는상품아이디 = 99;

        when(productDao.findById(any(int.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.update(없는상품아이디, 수정할테스트상품요청))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 id를 확인해주세요.");

        verify(productDao, atLeastOnce()).findById(any(int.class));
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void update() {
        int 존재하는상품아이디 = 1;
        String name = "테스트상품";
        String url = "http://www.naver.com";
        int price = 1000;

        ProductUpdateRequest 수정할테스트상품요청 = new ProductUpdateRequest(name, url, price);
        ProductEntity 수정된상품 = new ProductEntity(존재하는상품아이디, name, url, price);

        when(productDao.findById(any(int.class)))
                .thenReturn(Optional.of(수정된상품));

        productService.update(존재하는상품아이디, 수정할테스트상품요청);

        verify(productDao, atLeastOnce()).findById(any(int.class));
        verify(productDao, atLeastOnce()).update(any(ProductEntity.class));
    }

    @DisplayName("상품 조회 테스트")
    @Test
    void findAll() {
        ProductEntity 상품1 = new ProductEntity(1, "test", "http://www.naver.com", 1000);
        ProductEntity 상품2 = new ProductEntity(2, "test", "http://www.naver.com", 1000);
        ProductEntity 상품3 = new ProductEntity(3, "test", "http://www.naver.com", 1000);

        when(productDao.findAll())
                .thenReturn(List.of(상품1, 상품2, 상품3));

        List<ProductResponse> allProducts = productService.findAll();

        assertThat(allProducts).hasSize(3);
        verify(productDao, atLeastOnce()).findAll();
    }

    @DisplayName("없는 상품 삭제 시 예외 테스트")
    @Test
    void deleteFailByNonExistProductId() {
        int 없는상품아이디 = 99;

        when(productDao.findById(없는상품아이디))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.delete(없는상품아이디))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 id를 확인해주세요.");

        verify(productDao, atLeastOnce()).findById(any(int.class));
    }

    @DisplayName("상품 삭제 테스트")
    @Test
    void delete() {
        int 상품아이디 = 1;
        ProductEntity 상품 = new ProductEntity(1, "test", "http://www.naver.com", 1000);

        when(productDao.findById(상품아이디))
                .thenReturn(Optional.of(상품));

        productService.delete(상품아이디);

        verify(productDao, atLeastOnce()).findById(any(int.class));
        verify(productDao, atLeastOnce()).delete(any(int.class));
    }

}
