package cart.domain;

public class Product {

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MIN_PRICE = 1000;
    private static final int UNIT_OF_PRICE = 100;

    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public Product(String name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public Product(Long id, String name, int price, String imageUrl) {
        validateName(name);
        validatePrice(price);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    private void validateName(String name) {
        if (name.length() < MIN_NAME_LENGTH || MAX_NAME_LENGTH < name.length()) {
            throw new IllegalArgumentException(String.format("상품의 이름은 %d자 이상, %d자 이하입니다.", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
    }

    private void validatePrice(int price) {
        if (price < MIN_PRICE) {
            throw new IllegalArgumentException(String.format("상품의 최소 가격은 %d원 이상입니다.", MIN_PRICE));
        }

        if (price % UNIT_OF_PRICE != 0) {
            throw new IllegalArgumentException(String.format("상품의 가격 단위는 %d원 단위입니다.", UNIT_OF_PRICE));
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
