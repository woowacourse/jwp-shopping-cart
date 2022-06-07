package woowacourse.shoppingcart.dao.dto;

public class CartItem {

    private final Long cart_id;
    private final Long product_id;

    public CartItem(Long cart_id, Long product_id) {
        this.cart_id = cart_id;
        this.product_id = product_id;
    }

    public Long getCart_id() {
        return cart_id;
    }

    public Long getProduct_id() {
        return product_id;
    }
}
