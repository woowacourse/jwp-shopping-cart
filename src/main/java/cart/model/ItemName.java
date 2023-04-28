package cart.model;

import cart.exception.ErrorStatus;
import cart.exception.ItemException;

public class ItemName {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 50;

    private final String name;

    ItemName(String name) {
        validateName(name);

        this.name = name;
    }

    private void validateName(String name) {
        int length = name.length();

        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new ItemException(ErrorStatus.NAME_RANGE_ERROR);
        }
    }

    String getName() {
        return name;
    }
}
