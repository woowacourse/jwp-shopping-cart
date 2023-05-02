package cart.business;

import cart.business.domain.Product;
import cart.business.domain.ProductImage;
import cart.business.domain.ProductName;
import cart.business.domain.ProductPrice;
import cart.presentation.dto.ProductRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateProductServiceTest {

    private final ProductRepository productRepository = new MockProductRepository();
    private final ProductService createProductService = new ProductService(productRepository);

    @Test
    @DisplayName("동일한 상품을 repository에 insert할시 예외를 던진다")
    void test_perform_exception() {
        //given
        new ProductRequest("teo", "https://", 10);

        Product teo2 = new Product(2, new ProductName("teo"),
                new ProductImage("https://"), new ProductPrice(10));

        //when
        createProductService.create(new ProductRequest("teo", "https://", 10));

        //then
        Assertions.assertThatThrownBy(() -> createProductService.create(new ProductRequest("teo", "https://", 10)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
