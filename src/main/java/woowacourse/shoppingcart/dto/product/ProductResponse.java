package woowacourse.shoppingcart.dto.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import woowacourse.shoppingcart.domain.Product;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProductResponse {

    private long productId;
    private String thumbnailUrl;
    private String name;
    private int price;
    private long quantity;

    private ProductResponse() {
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getImageUrl(), product.getName(), product.getPrice(),
                product.getQuantity());
    }
}
