package woowacourse.shoppingcart.domain;

import static lombok.EqualsAndHashCode.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class CartItem {

    @Include
    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this(null, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
    }

    public CartItem(Long id, Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), 1);
    }

    public CartItem createWithId(Long id) {
        return new CartItem(id, productId, name, price, imageUrl, quantity);
    }
}
