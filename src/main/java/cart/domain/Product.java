package cart.domain;

import cart.dto.ProductRequest;

import java.math.BigDecimal;

public class Product {
    private static final int MAX_NAME_LENGTH = 50;
    private final String name;
    private final Image image;
    private final Price price;

    public Product(String name, String image, BigDecimal price) {
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

    public static Product from(ProductRequest productRequest) {
        return new Product(
                productRequest.getName(),
                productRequest.getImage(),
                new BigDecimal(productRequest.getPrice())
        );
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image.getUrl();
    }

    public Integer getPrice() {
        return price.getValue();
    }
}
