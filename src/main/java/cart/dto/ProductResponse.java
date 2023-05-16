package cart.dto;

import cart.entity.Product;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final String imgUrl;
    private final int price;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.imgUrl = product.getImgUrl();
        this.price = product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price;
    }
}
