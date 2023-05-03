package cart.business;

import cart.business.domain.product.Product;
import cart.business.domain.product.ProductImage;
import cart.business.domain.product.ProductName;
import cart.business.domain.product.ProductPrice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private final ProductRepository productRepository = new MockProductRepository();
    private final ProductService productService = new ProductService(productRepository);

    @Test
    @DisplayName("동일한 이름을 가진 상품을 create 할 시 예외를 던진다")
    void test_create() {
        //given
        Product teo = new Product(null, new ProductName("teo"),
                new ProductImage("https://"), new ProductPrice(10));

        Product sameNameTeo = new Product(null, new ProductName("teo"),
                new ProductImage("https://123"), new ProductPrice(2000));
        //when
        productService.create(teo);

        //then
        Assertions.assertThatThrownBy(() -> productService.create(sameNameTeo))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 상품에 대해 update 할 시 예외를 던진다")
    void test_update() {
        //given
        Product teo = new Product(1, new ProductName("teo"),
                new ProductImage("https://"), new ProductPrice(10));

        //when, then
        Assertions.assertThatThrownBy(() -> productService.update(teo))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 상품에 대해 delete 할 시 예외를 던진다")
    void test_delete() {
        //given
        Product teo = new Product(1, new ProductName("teo"),
                new ProductImage("https://"), new ProductPrice(10));

        //when, then
        Assertions.assertThatThrownBy(() -> productService.delete(teo.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
