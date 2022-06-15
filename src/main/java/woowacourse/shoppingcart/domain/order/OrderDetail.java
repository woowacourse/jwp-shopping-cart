package woowacourse.shoppingcart.domain.order;

import woowacourse.shoppingcart.domain.product.ImageUrl;
import woowacourse.shoppingcart.domain.product.Name;
import woowacourse.shoppingcart.domain.product.Price;

public class OrderDetail {

    private final Long productId;
    private final Quantity quantity;
    private final Price price;
    private final Name name;
    private final ImageUrl imageUrl;

    public OrderDetail(Long productId, Quantity quantity, Price price, Name name, ImageUrl imageUrl) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Long getProductId() {
        return productId;
    }

    public int getPrice() {
        return price.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public String getImageUrl() {
        return imageUrl.getValue();
    }

    public int getQuantity() {
        return quantity.getValue();
    }

    public int cost() {
        return price.getValue() * quantity.getValue();
    }
}
