package woowacourse.shoppingcart.dto.product;

import lombok.Getter;
import woowacourse.shoppingcart.domain.product.Product;

@Getter
public class ProductFindResponse {

    private long productId;
    private String name;
    private int price;
    private String image;

    public ProductFindResponse() {
    }

    public ProductFindResponse(long productId, String name, int price, String image) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public ProductFindResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImage());
    }
}
