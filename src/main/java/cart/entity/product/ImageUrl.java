package cart.entity.product;

final class ImageUrl {

    private static final int MAX_LENGTH = 255;

    private final String value;

    public ImageUrl(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이미지 경로가 존재하지 않습니다.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("이미지 경로 길이는 %d를 넘을 수 없습니다.", MAX_LENGTH));
        }
    }

    public String getValue() {
        return value;
    }
}
