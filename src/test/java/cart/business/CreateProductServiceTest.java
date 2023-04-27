package cart.business;

import cart.business.domain.Product;
import cart.business.domain.ProductImage;
import cart.business.domain.ProductName;
import cart.business.domain.ProductPrice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateProductServiceTest {

    private ProductRepository productRepository = new MockProductRepository();
    private CreateProductService createProductService = new CreateProductService(productRepository);

    @Test
    @DisplayName("동일한 상품을 repository에 insert할시 예외를 던진다")
    void test_perform_() {
        //given
        Product teo = new Product(null, new ProductName("teo"),
                new ProductImage("https://"), new ProductPrice(10));

        //when
        createProductService.perform(teo);

        //then
        Assertions.assertThatThrownBy(() -> createProductService.perform(teo))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
