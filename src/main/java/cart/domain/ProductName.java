package cart.domain;

import cart.domain.exception.WrongProductNameException;

public class ProductName {

    private final int MIN_LENGTH = 1;
    private final int MAX_LENGTH = 50;

    private final String name;

    public ProductName(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (name.length() > MAX_LENGTH || name.length() < MIN_LENGTH) {
            throw new WrongProductNameException();
        }
    }

    public String getName() {
        return name;
    }
}
