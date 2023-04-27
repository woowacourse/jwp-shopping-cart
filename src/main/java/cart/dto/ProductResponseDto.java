package cart.dto;

import cart.domain.Product;

public class ProductResponseDto {
    private final Long id;
    private final String name;
    private final String imgUrl;
    private final int price;

    public ProductResponseDto(Product product) {
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
