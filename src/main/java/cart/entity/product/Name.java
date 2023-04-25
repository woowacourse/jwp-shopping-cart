package cart.entity.product;

class Name {

    private static final int MAX_LENGTH = 50;

    private final String value;

    public Name(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("상품명이 존재하지 않습니다.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("상품명은 최대 %d자 까지 입니다.", MAX_LENGTH));
        }
    }

    public String getValue() {
        return value;
    }
}
