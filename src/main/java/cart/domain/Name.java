package cart.domain;

public class Name {

    private final String name;

    public Name(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (1 > name.length() || name.length() > 10) {
            throw new IllegalArgumentException("이름의 길이는 1~10자만 가능합니다.");
        }
    }

    public String getName() {
        return name;
    }
}
