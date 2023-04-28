package cart.entity.product;

final class Description {

    private static final int MAX_LENGTH = 255;

    private final String value;

    public Description(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("상품 설명은 최대 %d자 까지 입니다.", MAX_LENGTH));
        }
    }

    public String getValue() {
        return value;
    }
}
