package cart.dto;

import cart.domain.Product;

public class ProductDto {

    private String name;
    private Integer price;
    private String image;

    public ProductDto() {
    }

    public ProductDto(final String name, final Integer price, final String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product toProduct() {
        return new Product(name, price, image);
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
