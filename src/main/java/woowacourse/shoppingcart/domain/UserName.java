package woowacourse.shoppingcart.domain;

public class UserName {
    private static final String BLANK = " ";
    private final String name;

    public UserName(String name) {
        checkValid(name);
        this.name = name;
    }

    private void checkValid(String name) {
        if (name.contains(BLANK)) {
            throw new IllegalArgumentException("[ERROR] 이름에는 공백이 포함될 수 없습니다.");
        }

        if (name.length() > 32) {
            throw new IllegalArgumentException("[ERROR] 이름의 길이는 32자를 초과할 수 없습니다.");
        }
    }
}
