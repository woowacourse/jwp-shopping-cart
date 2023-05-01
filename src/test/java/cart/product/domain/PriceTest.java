package cart.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {
    
    @Test
    @DisplayName("가격 생성")
    void create() {
        final Price price = new Price(1000);
        assertEquals(1000, price.getValue());
    }
    
    @Test
    @DisplayName("가격이 0보다 작으면 예외가 발생한다.")
    void validateNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Price(-1000);
        });
    }
    
}