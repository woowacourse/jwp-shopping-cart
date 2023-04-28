package cart.model;

import cart.exception.item.ItemFieldNotValidException;

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
            throw new ItemFieldNotValidException("상품의 이름은 최소 1자, 최대 50자까지 입력할 수 있습니다.");
        }
    }

    String getName() {
        return name;
    }
}
