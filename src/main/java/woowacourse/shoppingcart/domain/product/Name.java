package woowacourse.shoppingcart.domain.product;

public class Name {

    private static final int LENGTH_LIMIT = 255;

    private final String value;

    public Name(String name) {
        validateNameLength(name);
        this.value = name;
    }

    private void validateNameLength(String name) {
        if (name.length() > LENGTH_LIMIT) {
            throw new IllegalArgumentException("상품 이름 길이는 255자를 초과할 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
