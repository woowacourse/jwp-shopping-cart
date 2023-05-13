package cart.entity.product;

import cart.exception.common.NullOrBlankException;

public class Name {

    private final String name;

    public Name(final String name) {
        validateNullOrBlank(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validateNullOrBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new NullOrBlankException();
        }
    }
}
