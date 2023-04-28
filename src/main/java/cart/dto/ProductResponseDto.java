package cart.dto;

import cart.entity.Product;

public class ProductResponseDto {
    private final Long id;
    private final String name;
    private final String image;
    private final int price;

    public ProductResponseDto(Long id, String name, String image, int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getImage(), product.getPrice());
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
