package cart.entity;

public class Product {

    private static final int MIN_PRICE = 0;
    private static final String URL_REGEX = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";

    private final Long id;
    private String name;
    private int price;
    private String imageUrl;

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        validateId(id);
        validateName(name);
        validatePrice(price);
        validateImageUrl(imageUrl);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private void validateId(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id값이 null입니다.");
        }
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name이 존재해야 합니다.");
        }
    }

    private void validatePrice(final int price) {
        if (price < 0) {
            throw new IllegalArgumentException("price는 " + MIN_PRICE + " 이상의 자연수 여야 합니다.");
        }
    }

    private void validateImageUrl(final String imageUrl) {
        if (!imageUrl.matches(URL_REGEX)) {
            throw new IllegalArgumentException("imageUrl값이 형식에 맞지 않습니다.");
        }
    }

    public void setName(final String name) {
        validateName(name);
        this.name = name;
    }

    public void setPrice(final int price) {
        validatePrice(price);
        this.price = price;
    }

    public void setImageUrl(final String imageUrl) {
        validateImageUrl(imageUrl);
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }
}
