package cart.entity;

public class Product {

    private static final int MAX_NAME_LENGTH = 255;
    private static final int MIN_NAME_LENGTH = 0;
    private static final String IMAGE_EXTENSION_FORMAT = ".*\\.(jpg|jpeg|png|webp|avif|gif|svg)$";

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    public Product(final Long id, final String name, final String imageUrl, final Integer price) {
        validateImageUrl(imageUrl);
        validatePrice(price);
        validateName(name);
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(final String name, final String imageUrl, final Integer price) {
        this(null, name, imageUrl, price);
    }

    private void validateName(final String name) {
        if (name.length() > MAX_NAME_LENGTH || name.length() == MIN_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 0자 초과 255미만이어야 합니다.");
        }
    }

    private void validatePrice(final int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0보다 작을 수 없습니다.");
        }
    }

    private void validateImageUrl(final String imageUrl) {
        if (!imageUrl.matches(IMAGE_EXTENSION_FORMAT)) {
            throw new IllegalArgumentException("유효한 이미지 확장자가 아닙니다.");
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }
}
