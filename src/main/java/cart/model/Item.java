package cart.model;

import cart.exception.ErrorStatus;
import cart.exception.ItemException;

public class Item {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 50;
    private static final int MIN_PRICE = 10;
    private static final int MAX_PRICE = 100_000_000;

    private final String name;
    private final String imageUrl;
    private final int price;

    public Item(String name, String imageUrl, int price) {
        validateName(name);
        validatePrice(price);
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    private void validateName(String name) {
        int length = name.length();

        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new ItemException(ErrorStatus.NAME_RANGE_ERROR);
        }
    }

    private void validatePrice(int price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new ItemException(ErrorStatus.PRICE_RANGE_ERROR);
        }
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
