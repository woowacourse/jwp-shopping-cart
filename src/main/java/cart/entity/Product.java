package cart.entity;

public final class Product {
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_PRICE = 100000000;
    private static final int MIN_PRICE = 0;
    private static final int UNIT_OF_PRICE = 10;

    private final Long id;
    private final String name;
    private final String image;
    private final int price;

    public Product(String name, String image, int price) {
        this(null, name, image, price);
    }

    public Product(Long id, String name, String image, int price) {
        validate(name, price);
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    private void validate(String name, final int price) {
        if (name.isBlank() || MAX_NAME_LENGTH < name.length()) {
            throw new IllegalArgumentException("상품 이름은 1 ~ 20 길이여야 합니다.");
        }

        if (price < MIN_PRICE || MAX_PRICE < price) {
            throw new IllegalArgumentException("상품 가격은 0원 이상 1억원 이하여야 합니다.");
        }

        if (price % UNIT_OF_PRICE != 0) {
            throw new IllegalArgumentException("상품 가격은 10원 단위여야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }


}
