package cart.dto.entity;

public class MemberCartEntity {
    private final Long product_id;
    private final Long member_id;
    private final String product_name;
    private final String product_image;
    private final Integer product_price;

    public MemberCartEntity(Long product_id, Long member_id, String product_name, String product_image, Integer product_price) {
        this.product_id = product_id;
        this.member_id = member_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public Long getMember_id() {
        return member_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public Integer getProduct_price() {
        return product_price;
    }
}
