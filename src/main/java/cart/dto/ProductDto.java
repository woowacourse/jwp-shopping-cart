package cart.dto;

import cart.domain.Product;

public class ProductDto {

    private final Long id;
    private final String name;
    private final String imgUrl;
    private final int price;

    public ProductDto(Long id, String name, String imgUrl, int price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public ProductDto(Product product) {
        id = product.getId();
        name = product.getName();
        imgUrl = product.getImgUrl();
        price = product.getPrice();
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

    public Long getId() {
        return id;
    }
}
