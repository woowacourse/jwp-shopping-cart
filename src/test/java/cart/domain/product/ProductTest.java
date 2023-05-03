package cart.domain.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("name, image, price로 product를 생성할 수 있다.")
    public void createByFourParameters() {
        final long id = 1L;
        final Name name = new Name("망고");
        final String image = "http://mango";
        final Price price = new Price(1000);
        final Product product = new Product(id, name, image, price);

        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(image, product.getImage());
        assertEquals(price, product.getPrice());
    }

    @Test
    @DisplayName("name, image, price로 product를 생성할 수 있다.")
    public void createByThreeParameters() {
        final Name name = new Name("망고");
        final String image = "http://mango";
        final Price price = new Price(1000);
        final Product product = new Product(name, image, price);

        assertEquals(name, product.getName());
        assertEquals(image, product.getImage());
        assertEquals(price, product.getPrice());
    }
}
