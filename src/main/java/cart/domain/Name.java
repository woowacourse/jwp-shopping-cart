package cart.domain;

public final class Name {

    public static final int MAX_LENGTH_INCLUSIVE = 10;
    private final String name;

    private Name(String name) {
        this.name = name;
    }

    public static Name of(String name) {
        if (name.length() >= MAX_LENGTH_INCLUSIVE) {
            throw new IllegalArgumentException("사용자의 이름은 10자 이내로 입력해 주세요!");
        }
        return new Name(name);
    }

    public String getName() {
        return name;
    }
}
