package cart.vo;

public class Url {

    private final String value;

    private Url(String value) {
        this.value = value;
    }

    public static Url of(String value) {
        validateUrl(value);
        return new Url(value);
    }

    private static void validateUrl(String value) {
        if (value.isBlank() || value.isEmpty()) {
            throw new IllegalStateException("올바르지 않은 링크입니다.");
        }
    }

    public String getValue() {
        return value;
    }

}
