package woowacourse.shoppingcart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDetail {

    private Long productId;
    private int price;
    private String name;
    private String imageUrl;
    private int quantity;

    public OrderDetail(CartItem cartItem) {
        this(cartItem.getProductId(),
            cartItem.getPrice(),
            cartItem.getName(),
            cartItem.getImageUrl(),
            cartItem.getQuantity());
    }

    public int calculatePrice() {
        return price * quantity;
    }
}
