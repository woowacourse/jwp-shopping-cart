package woowacourse.auth.domain;

public class EncryptedPassword {

    private final String value;

    public EncryptedPassword(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
