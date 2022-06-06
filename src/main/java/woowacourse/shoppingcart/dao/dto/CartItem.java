package woowacourse.shoppingcart.dao.dto;

public class CartItem {

    private final Long member_id;
    private final Long product_id;

    public CartItem(Long member_id, Long product_id) {
        this.member_id = member_id;
        this.product_id = product_id;
    }

    public Long getMember_id() {
        return member_id;
    }

    public Long getProduct_id() {
        return product_id;
    }
}
