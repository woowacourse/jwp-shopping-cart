package cart.dto;

import cart.domain.Product;

public class ProductDto {

    private Long productId;
    private final String name;
    private final String image;
    private final long price;

    public ProductDto(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName().getName();
        this.image = product.getImage();
        this.price = product.getPrice().getPrice();
    }

    public ProductDto(Long productId, String name, String image, long price) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public long getPrice() {
        return price;
    }
}
