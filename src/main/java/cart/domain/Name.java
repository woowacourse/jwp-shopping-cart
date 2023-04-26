package cart.domain;

public class Name {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 10;

    private final String name;

    public Name(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (MIN_LENGTH > name.length() || name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("이름의 길이는 1~10자만 가능합니다.");
        }
    }

    public String getName() {
        return name;
    }
}
