package cart.service.dto;

import cart.domain.Price;
import cart.domain.Product;
import cart.domain.ProductName;

public class ProductDto {

    private String name;
    private int price;
    private String imgUrl;

    public ProductDto(final String imgUrl, final String name, final int price) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public Product toDomain() {
        return new Product(new ProductName(name), imgUrl, new Price(price));
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
