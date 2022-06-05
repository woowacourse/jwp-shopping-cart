package woowacourse.product.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import woowacourse.product.domain.Product;

@JsonTypeName("product")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private int stock;
    private String imageURL;

    private ProductResponse() {
    }

    public ProductResponse(final Long id, final String name, final int price, final int stock, final String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public static ProductResponse from(final Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice().getValue(),
            product.getStock().getValue(),
            product.getImageURL()
        );
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

    public int getStock() {
        return stock;
    }

    public String getImageURL() {
        return imageURL;
    }
}
