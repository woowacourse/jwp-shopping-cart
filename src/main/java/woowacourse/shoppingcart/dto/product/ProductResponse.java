package woowacourse.shoppingcart.dto.product;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import woowacourse.shoppingcart.domain.Product;

@JsonTypeName("product")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private Integer stock;
    private String imageURL;

    private ProductResponse() {
    }

    public ProductResponse(final Long id, final String name, final Integer price, final Integer stock,
                           final String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public static ProductResponse from(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock(),
                product.getImageUrl());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getImageURL() {
        return imageURL;
    }
}
