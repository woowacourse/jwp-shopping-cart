package cart.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import cart.entity.Product;

public class ProductRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    @URL
    private String imgUrl;
    @NotNull
    private Integer price;

    public ProductRequest() {
    }

    public ProductRequest(String name, String imgUrl, int price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public Product toProduct() {
        return Product.of(
            null,
            this.name,
            this.imgUrl,
            this.price
        );
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
