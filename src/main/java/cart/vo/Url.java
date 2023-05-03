package cart.vo;

import java.util.Objects;

public class Url {

    private final String value;

    private Url(String value) {
        this.value = value;
    }

    public static Url from(String value) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return Objects.equals(value, url.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
