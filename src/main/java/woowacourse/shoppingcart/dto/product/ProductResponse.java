package woowacourse.shoppingcart.dto.product;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import woowacourse.shoppingcart.domain.product.Product;

@JsonTypeName("product")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
public class ProductResponse {

    private long id;
    private String name;
    private int price;
    private int stock;
    private String imageURL;

    private ProductResponse() {
    }

    public ProductResponse(long id, String name, int price, int stock, String imageURL) {
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

    public long getId() {
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
