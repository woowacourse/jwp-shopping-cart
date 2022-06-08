package woowacourse.shoppingcart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDetail {

    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;

    public OrderDetail(CartItem cartItem) {
        this(cartItem.getProductId(),
            cartItem.getName(),
            cartItem.getPrice(),
            cartItem.getImageUrl(),
            cartItem.getQuantity());
    }

    public OrderDetail(Product product, int quantity) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
    }

    public int calculatePrice() {
        return price * quantity;
    }
}
