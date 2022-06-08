package woowacourse.shoppingcart.dto.product;

import lombok.Getter;
import woowacourse.shoppingcart.domain.Product;

@Getter
public class ProductResponse {

    private final long id;
    private final String thumbnailUrl;
    private final String name;
    private final long price;
    private final long quantity;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.thumbnailUrl = product.getImageUrl();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }
}
