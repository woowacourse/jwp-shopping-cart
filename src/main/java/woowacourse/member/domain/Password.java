package woowacourse.member.domain;

import java.util.Objects;

public class Password {

    private final String value;

    protected Password(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass().isInstance(Password.class)) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
