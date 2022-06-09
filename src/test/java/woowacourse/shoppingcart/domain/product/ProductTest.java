package woowacourse.shoppingcart.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @DisplayName("같은 상품인지 확인한다.")
    @Test
    void isSame() {
        Product product = new Product(new ProductId(1), new Name("coffee"), new Price(10000), new Thumbnail("coffee.png"));
        final Boolean result = product.isSame(product);

        assertThat(result).isTrue();
    }
}
