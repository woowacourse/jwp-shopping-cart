package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.ImageUrl;
import woowacourse.shoppingcart.domain.product.Name;
import woowacourse.shoppingcart.domain.product.Price;

public class CartItem {

    private final long productId;

    private final Name productName;
    private final Price productPrice;
    private final ImageUrl productImageUrl;

    public CartItem(long productId, Name productName, Price productPrice, ImageUrl productImageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageUrl = productImageUrl;
    }

    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName.getValue();
    }

    public int getProductPrice() {
        return productPrice.getValue();
    }

    public String getProductImageUrl() {
        return productImageUrl.getValue();
    }
}
