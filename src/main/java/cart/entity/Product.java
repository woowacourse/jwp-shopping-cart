package cart.entity;

import cart.exception.customexceptions.NotValidDataException;

public class Product {

    private static final int MIN_PRICE = 0;
    private static final int MAX_PRICE = 1_000_000_000;

    private Long id;
    private String name;
    private String imageUrl;
    private int price;

    public Product(final Long id, final String name, final String imageUrl, final int price) {
        validateEmpty(name);
        validateEmpty(imageUrl);
        validatePriceRange(price);
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    public void validateEmpty(final String value) {
        if (value.trim().isEmpty()) {
            throw new NotValidDataException("값은 공백일 수 없습니다.");
        }
    }

    public void validatePriceRange(final int price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new NotValidDataException("가격은 10억 이하의 음이 아닌 정수여야 합니다.");
        }
    }

    public Long getId() {
        return id;
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
