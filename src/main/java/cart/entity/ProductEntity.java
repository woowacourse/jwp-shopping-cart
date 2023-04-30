package cart.entity;

import cart.dto.ProductRequest;

public class ProductEntity {

    private final Long id;
    private String name;
    private String image;
    private int price;

    public ProductEntity(Long id, String name, String image, int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public void replace(ProductRequest productRequest) {
        this.name = productRequest.getName();
        this.image = productRequest.getImage();
        this.price = productRequest.getPrice();
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
