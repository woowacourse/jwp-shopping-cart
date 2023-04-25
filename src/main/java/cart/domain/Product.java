package cart.domain;

public class Product {

    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 64;
    public static final String NAME_LENGTH_ERROR_MESSAGE = "이름의 길이는 " + MIN_NAME_LENGTH + "자 이상 " + MAX_NAME_LENGTH + "자 이하입니다.";
    public static final int MAX_PRICE = 10_000_000;
    public static final String PRICE_ERROR_MESSAGE = "상품의 가격은 " + MAX_PRICE + "를 초과할 수 없습니다.";
    
    private final Long id;
    private final String name;
    private final int price;
    private final String image;

    public Product(Long id, String name, int price, String image) {
        validate(name, price, image);
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public void validate(String name, int price, String image) {
        if (name.length() < MIN_NAME_LENGTH || MAX_NAME_LENGTH < name.length()) {
            throw new IllegalArgumentException(NAME_LENGTH_ERROR_MESSAGE);
        }
        if (price > MAX_PRICE) {
            throw new IllegalArgumentException(PRICE_ERROR_MESSAGE);
        }
    }

    public Product(String name, int price, String image) {
        validate(name, price, image);
        this.id = null;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
