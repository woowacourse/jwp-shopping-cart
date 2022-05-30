package woowacourse.auth.domain.customer;

import java.util.Objects;
import woowacourse.auth.exception.format.InvalidUrlFormatException;

public class ProfileImageUrl {
    private static final String URL_REGEX = "^(http(s)?:\\/\\/)[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.([a-zA-Z])+.*";

    private final String value;

    public ProfileImageUrl(String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(String value) {
        if (Objects.isNull(value) || !value.matches(URL_REGEX)) {
            throw new InvalidUrlFormatException();
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfileImageUrl that = (ProfileImageUrl) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ProfileImageUrl{" +
                "value='" + value + '\'' +
                '}';
    }
}
