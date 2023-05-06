package shoppingbasket.product.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppingbasket.product.dao.ProductDao;
import shoppingbasket.product.dto.ProductInsertRequestDto;
import shoppingbasket.product.dto.ProductUpdateRequestDto;
import shoppingbasket.product.entity.ProductEntity;

class ProductServiceTest {

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(new FakeProductDao());
    }

    @Test
    void addProduct_imageNotUrl_fail() {
        final ProductInsertRequestDto request = new ProductInsertRequestDto("name", "nonImageUrl", 1000);

        assertThatThrownBy(() -> productService.addProduct(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 주소는 URL 형태로 입력되어야 합니다.");
    }

    @Test
    void updateProduct_imageNotUrl_fail() {
        final ProductUpdateRequestDto request = new ProductUpdateRequestDto(1, "nonImageUrl", "name", 1000);

        assertThatThrownBy(() -> productService.updateProduct(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 주소는 URL 형태로 입력되어야 합니다.");
    }

    static class FakeProductDao implements ProductDao {

        @Override
        public ProductEntity insert(final ProductEntity product) {
            return null;
        }

        @Override
        public List<ProductEntity> selectAll() {
            return null;
        }

        @Override
        public ProductEntity findById(final int productId) {
            return null;
        }

        @Override
        public ProductEntity update(final ProductEntity product) {
            return null;
        }

        @Override
        public int delete(final int productId) {
            return 0;
        }
    }
}
