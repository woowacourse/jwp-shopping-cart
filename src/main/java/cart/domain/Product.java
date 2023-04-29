package cart.domain;

public class Product {

    private static final int MAX_NAME_LENGTH = 50;
    
    private final String name;
    private final Image image;
    private final Price price;

    public Product(String name, String image, long price) {
        validateName(name);
        this.name = name;
        this.image = new Image(image);
        this.price = new Price(price);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 비어있을 수 없습니다.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("상품명은 50자를 초과할 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image.getUrl();
    }

    public long getPrice() {
        return price.getValue();
    }
}
