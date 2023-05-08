package cart.product.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class Name {
    private static final int MAX_NAME_LENGTH = 255;
    
    private final String name;
    
    public Name(final String name) {
        validateName(name);
        this.name = name;
    }
    
    private void validateName(final String name) {
        validateBlank(name);
        validateOutOgLength(name);
    }
    
    private void validateBlank(final String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 상품 이름을 입력해주세요.");
        }
    }
    
    private void validateOutOgLength(final String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 상품 이름의 길이가 255자를 넘어섰습니다.");
        }
    }
}
