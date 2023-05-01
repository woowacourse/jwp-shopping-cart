package cart.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class Price {
    private static final int MAX_PRICE = 10_000_000;
    private static final int MIN_PRICE = 0;
    
    private final Integer price;
    
    public Price(final Integer price) {
        validatePrice(price);
        this.price = price;
    }
    
    private void validatePrice(final Integer price) {
        validateNull(price);
        validateOutOfRange(price);
    }
    
    private void validateNull(final Integer price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException("[ERROR] 상품 가격을 입력해주세요.");
        }
    }
    
    private void validateOutOfRange(final Integer price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException("[ERROR] 입력할 수 있는 가격의 범위를 벗어났습니다.");
        }
    }
}
