package woowacourse.shoppingcart.dto.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.Request;

public class ProductRequest {

    @NotNull(groups = {Request.id.class})
    private Long id;

    @NotBlank(groups = {Request.allProperties.class})
    private String name;

    @Positive(groups = {Request.allProperties.class})
    private int price;

    @NotBlank(groups = {Request.allProperties.class})
    private String imageUrl;

    @Positive(groups = {Request.allProperties.class})
    private int stock;

    private ProductRequest() {
    }

    public ProductRequest(Long id, String name, int price, String imageUrl, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public Product toProduct() {
        return new Product(name, price, imageUrl, stock);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getStock() {
        return stock;
    }
}
