package cart.business;

import cart.business.domain.Product;
import cart.business.domain.ProductImage;
import cart.business.domain.ProductName;
import cart.business.domain.ProductPrice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductCRUDServiceTest {

    private final ProductRepository productRepository = new MockProductRepository();
    private final ProductCRUDService productCRUDService = new ProductCRUDService(productRepository);

    @Test
    @DisplayName("동일한 이름을 가진 상품을 create 할 시 예외를 던진다")
    void test_create() {
        //given
        Product teo = new Product(null, new ProductName("teo"),
                new ProductImage("https://"), new ProductPrice(10));

        Product sameNameTeo = new Product(null, new ProductName("teo"),
                new ProductImage("https://123"), new ProductPrice(2000));
        //when
        productCRUDService.create(teo);

        //then
        Assertions.assertThatThrownBy(() -> productCRUDService.create(sameNameTeo))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 상품에 대해 update 할 시 예외를 던진다")
    void test_update() {
        //given
        Product teo = new Product(1, new ProductName("teo"),
                new ProductImage("https://"), new ProductPrice(10));

        //when, then
        Assertions.assertThatThrownBy(() -> productCRUDService.update(teo))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
