package cart.entity;

import cart.dto.ProductRequest;

public class ProductEntity {

    private final Long id;
    private String name;
    private String imageUrl;
    private int price;

    public ProductEntity(Long id, String name, String image, int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = image;
        this.price = price;
    }

    public void replace(ProductRequest productRequest) {
        this.name = productRequest.getName();
        this.imageUrl = productRequest.getImageUrl();
        this.price = productRequest.getPrice();
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
}
