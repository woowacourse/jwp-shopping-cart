package cart.entity;

import cart.dto.ProductRequest;

public class ProductEntity {

    private final Long id;
    private String name;
    private String image;
    private Integer price;

    public ProductEntity(Long id, String name, String image, Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public void update(ProductRequest productRequest) {
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

    public Integer getPrice() {
        return price;
    }
}
