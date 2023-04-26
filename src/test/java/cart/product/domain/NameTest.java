package cart.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {
    
    @Test
    @DisplayName("이름 생성")
    void create() {
        final Name name = new Name("스파게티");
        assertEquals("스파게티", name.getValue());
    }
    
    @Test
    @DisplayName("이름이 10자를 초과하면 예외가 발생한다.")
    void validateLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Name("스파게티스파게티티티티");
        });
    }
    
    @Test
    @DisplayName("이름이 비어있으면 예외가 발생한다.")
    void validateEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Name("");
        });
    }
}