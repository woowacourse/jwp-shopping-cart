package woowacourse.shoppingcart.dto.product;

import lombok.Getter;
import woowacourse.shoppingcart.domain.Product;

@Getter
public class ProductResponse {

    private long productId;
    private String thumbnailUrl;
    private String name;
    private int price;
    private long quantity;

    private ProductResponse() {
    }

    public ProductResponse(Product product) {
        this.productId = product.getId();
        this.thumbnailUrl = product.getImageUrl();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }
}
