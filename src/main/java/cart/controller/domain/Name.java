package cart.controller.domain;

public class Name {

    private static final String LENGTH_ERROR_MESSAGE = "이름은 1글자 이상 50글자 이하여야합니다.";

    private final String name;

    public Name(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name.length() < 1 || name.length() > 50) {
            throw new IllegalArgumentException(LENGTH_ERROR_MESSAGE);
        }
    }

    public String getName() {
        return name;
    }
}
