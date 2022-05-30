package woowacourse.auth.domain;

import java.util.Objects;

public class Nickname {

    private final String value;

    public Nickname(String value) {
        value = value.trim();
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nickname)) {
            return false;
        }
        Nickname nickname = (Nickname) o;
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
