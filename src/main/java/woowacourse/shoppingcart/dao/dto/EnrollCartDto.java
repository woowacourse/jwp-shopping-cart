package woowacourse.shoppingcart.dao.dto;

public class EnrollCartDto {

    private final Long member_id;
    private final Long product_id;
    private final int quantity;

    public EnrollCartDto(Long member_id, Long product_id) {
        this.member_id = member_id;
        this.product_id = product_id;
        this.quantity = 1;
    }

    public Long getMember_id() {
        return member_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }
}
