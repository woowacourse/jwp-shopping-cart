package cart.entity.product;

final class ImageUrl {

    private final String value;

    public ImageUrl(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이미지 경로가 존재하지 않습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
