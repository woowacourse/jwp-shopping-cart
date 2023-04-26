package cart.service;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.entity.ProductEntity;
import cart.fixture.ProductFixture;
import cart.repository.ProductDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static cart.fixture.ProductFixture.COFFEE;
import static cart.fixture.ProductFixture.RAMYEON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;


    @Test
    void 상품을_저장한다() {
        ProductRequest productRequest = new ProductRequest("image", "name", 1000);
        ProductResponse productResponse = productService.create(productRequest);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(productResponse.getName()).isEqualTo(productRequest.getName()),
                () -> assertThat(productResponse.getPrice()).isEqualTo(productRequest.getPrice()),
                () -> assertThat(productResponse.getImage()).isEqualTo(productRequest.getImage())
        );
    }

    @Nested
    class 유효하지_않은_상품정보를_저장하면_예외 {
        @Test
        void 유효하지_않은_상품명() {
            final String invalidProductName = "a".repeat(51);
            ProductRequest request = new ProductRequest("url", invalidProductName, 1000);

            assertThatThrownBy(() -> productService.create(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("상품명은 50자를 초과할 수 없습니다.");
        }

        @Test
        void 유효하지_않은_url() {
            ProductRequest request = new ProductRequest(null, "name", 1000);
            
            assertThatThrownBy(() -> productService.create(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미지 URL은 비어있을 수 없습니다.");
        }

        @Test
        void 유효하지_않은_가격() {
            ProductRequest request = new ProductRequest("image", "name", -1);

            assertThatThrownBy(() -> productService.create(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("가격은 음수 혹은 빈 값이 될 수 없습니다.");
        }
    }

    @Test
    void 상품_목록을_조회한다() {
        List<Product> products = List.of(ProductFixture.RAMYEON, ProductFixture.WATER, ProductFixture.COFFEE);
        for (Product product : products) {
            productDao.save(product);
        }
        List<ProductResponse> results = productService.findAll();

        assertThat(results).hasSize(products.size());
    }

    @Test
    void 상품정보를_수정한다() {
        Product ramyeon = RAMYEON;
        Optional<ProductEntity> saved = productDao.save(ramyeon);
        ProductEntity product = saved.get();
        Long productId = product.getId();

        ProductRequest updateRequest = new ProductRequest("expectedUrl", "expected", 1000);

        ProductResponse updatedResponse = productService.update(updateRequest, productId);

        Assertions.assertAll(
                () -> assertThat(updatedResponse.getImage()).isEqualTo(updateRequest.getImage()),
                () -> assertThat(updatedResponse.getName()).isEqualTo(updateRequest.getName()),
                () -> assertThat(updatedResponse.getPrice()).isEqualTo(updateRequest.getPrice())
        );
    }

    @Test
    void 존재하지_않는_상품정보를_수정하면_예외가_발생한다() {
        Long productId = 1L;
        ProductRequest updateRequest = new ProductRequest("expectedUrl", "expected", 1000);


        assertThatThrownBy(() -> productService.update(updateRequest, productId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + productId);
    }

    @Test
    void 상품을_삭제한다() {
        ProductEntity product = productDao.save(COFFEE).get();
        Long productId = product.getId();

        Assertions.assertDoesNotThrow(() -> productService.deleteById(productId));
    }

    @Test
    void 존재하지_않는_상품을_삭제하면_예외가_발생한다() {
        Long productId = 1L;

        assertThatThrownBy(() -> productService.deleteById(productId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + productId);
    }
}
