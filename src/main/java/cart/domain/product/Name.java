package cart.domain.product;

import org.springframework.util.StringUtils;

public class Name {

    private static final int MAX_LENGTH = 255;

    private final String name;

    private Name(final String name) {
        validate(name);

        this.name = name;
    }

    public static Name from(final String name) {
        return new Name(name);
    }

    private void validate(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("상품명이 존재하지 않습니다.");
        }

        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("상품명은 최대 %d 글자까지 가능합니다", MAX_LENGTH));
        }
    }

    public String getName() {
        return name;
    }
}
