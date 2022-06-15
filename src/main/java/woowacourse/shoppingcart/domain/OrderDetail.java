package woowacourse.shoppingcart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDetail {

    private Product product;
    private Quantity quantity;

    public OrderDetail(CartItem cartItem) {
        this(cartItem.getProduct(), cartItem.getQuantity());
    }

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = new Quantity(quantity);
    }


    public int calculatePrice() {
        return product.multiplyPrice(quantity.getValue());
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}
