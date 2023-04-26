package cart.model;

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
            throw new IllegalArgumentException("상품의 이름은 최소 1자, 최대 50자까지 가능합니다.");
        }
    }

    private void validatePrice(int price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException("상품의 금액은 최소 10원, 최대 1억원 까지 가능합니다.");
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
