package woowacourse.shoppingcart.domain.product;

public class Description {

    private final String value;

    public Description(String value) {
        checkLength(value);
        this.value = value;
    }

    private void checkLength(String value) {
        if (value.length() > 225) {
            throw new IllegalArgumentException("상세 설명 길이가 올바르지 않습니다. (길이: 255자 이내)");
        }
    }

    public String getValue() {
        return value;
    }
}
