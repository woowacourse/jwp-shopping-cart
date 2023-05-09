package cart.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.exception.notfound.ProductNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProductServiceTest {

    private ProductDao productDao;
    private ProductService productService;


    @BeforeEach
    void setUp() {
        productDao = Mockito.mock(ProductDao.class);
        productService = new ProductService(productDao);
    }

    @Test
    @DisplayName("상품 갱신시 id가 유효하지 않으면 예외 발생")
    void updateInvalidId() {
        // given
        long id = 1;
        given(productDao.findById(id)).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> productService.update(id, "피자", 1000, "image"))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("상품 갱신시 id가 유효하면 예외가 발생하지 않는다")
    void update() {
        // given
        long id = 1;
        given(productDao.findById(id)).willReturn(Optional.of(new Product(id, "피자", 1000, "image")));

        // when then
        assertThatNoException().isThrownBy(() -> productService.update(id, "햄버거", 3000, null));
    }

    @Test
    @DisplayName("상품 삭제시 id가 유효하지 않으면 예외 발생")
    void deleteInvalidId() {
        // given
        long id = 1;
        given(productDao.findById(id))
                .willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> productService.delete(id))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("상품 삭제시 id가 유효하면 예외가 발생하지 않는다")
    void delete() {
        // given
        long id = 1;
        given(productDao.findById(id))
                .willReturn(Optional.of(new Product(id, "피자", 1000, "image")));

        // when then
        assertThatNoException().isThrownBy(() -> productService.delete(id));
    }
}
