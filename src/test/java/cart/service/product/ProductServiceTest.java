package cart.service.product;

import cart.fixture.ProductFixture;
import cart.service.product.domain.Product;
import cart.service.product.dto.ProductResponse;
import cart.service.product.dto.ProductServiceRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static cart.fixture.ProductFixture.COFFEE;
import static cart.fixture.ProductFixture.RAMYEON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@Sql("/test.sql")
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;


    @Test
    void 상품을_저장한다() {
        productService.create(new ProductServiceRequest("name", "image", 1000));

        List<ProductResponse> products = productService.findAll();

        assertThat(products).hasSize(1);
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
        Long productId = productDao.save(ramyeon);

        ProductServiceRequest updateRequest = new ProductServiceRequest("expectedUrl", "expected", 1000);

        ProductResponse updatedResponse = productService.update(
                updateRequest,
                productId
        );

        Assertions.assertAll(
                () -> assertThat(updatedResponse.getImageUrl()).isEqualTo(updateRequest.getImageUrl()),
                () -> assertThat(updatedResponse.getName()).isEqualTo(updateRequest.getName()),
                () -> assertThat(updatedResponse.getPrice()).isEqualTo(updateRequest.getPrice())
        );
    }

    @Test
    void 존재하지_않는_상품정보를_수정하면_예외가_발생한다() {
        Long productId = 0L;
        ProductServiceRequest updateRequest = new ProductServiceRequest("expectedUrl", "expected", 1000);


        assertThatThrownBy(() -> productService.update(
                updateRequest,
                productId)
        )
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + productId);
    }

    @Test
    void 상품을_삭제한다() {
        Long productId = productDao.save(COFFEE);

        Assertions.assertDoesNotThrow(() -> productService.deleteById(productId));
    }

    @Test
    void 존재하지_않는_상품을_삭제하면_예외가_발생한다() {
        Long productId = 0L;

        assertThatThrownBy(() -> productService.deleteById(productId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + productId);
    }
}
