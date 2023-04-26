package cart.domain;

public class Name {

    public static final int MAXIMUM_NAME_LENGTH = 10;
    public static final int MINIMUM_NAME_LENGTH = 1;
    public static final String NAME_LENGTH_VALID_MESSAGE = "[ERROR] 이름의 길이는 1~10자만 가능합니다.";
    private final String name;

    public Name(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (MINIMUM_NAME_LENGTH > name.length() || name.length() > MAXIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException(NAME_LENGTH_VALID_MESSAGE);
        }
    }

    public String getName() {
        return name;
    }
}
