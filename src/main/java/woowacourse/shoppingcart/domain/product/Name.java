package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.exception.InvalidNameException;

public class Name {

    private final String name;

    public Name(String name) {
        this.name = name;
        validate();
    }

    private void validate() {
        if (name.isBlank()) {
            throw new InvalidNameException();
        }
    }

    public String value() {
        return name;
    }
}
