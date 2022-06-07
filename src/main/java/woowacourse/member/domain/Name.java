package woowacourse.member.domain;

import woowacourse.member.exception.NameNotValidException;

public class Name {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 10;

    private String name;

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH) {
            throw new NameNotValidException();
        }
    }

    public void updateName(final String name) {
        validateName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
