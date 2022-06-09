package woowacourse.shoppingcart.domain.customer;

public class Name {

    private static final int MAXIMUM_NAME_LENGTH = 30;

    private final String name;

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    private static void validateName(String name) {
        if (name.isBlank() || name.length() > MAXIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("올바르지 않은 이름 형식입니다.");
        }
    }

    public String getValue() {
        return name;
    }
}
