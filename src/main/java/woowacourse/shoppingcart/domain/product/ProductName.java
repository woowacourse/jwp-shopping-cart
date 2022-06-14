package woowacourse.shoppingcart.domain.product;

public class ProductName {

    private final String value;

    private ProductName(String value) {
        validate(value);
        this.value = value;
    }

    public static ProductName of(String value) {
        return new ProductName(value);
    }

    private void validate(String value) {
        if (value.length() <= 0) {
            throw new IllegalArgumentException("상품명의 길이는 1이상이어야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
