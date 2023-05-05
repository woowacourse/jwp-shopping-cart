package cart.dto;

import cart.entity.Product;

public class ProductResponseDto {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public ProductResponseDto() {
    }

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
