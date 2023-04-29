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
    @DisplayName("동일한 이름을 가진 상품을 repository에 insert할시 예외를 던진다")
    void test_perform_() {
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
}
