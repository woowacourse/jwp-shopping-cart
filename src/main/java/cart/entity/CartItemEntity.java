package cart.entity;

public class CartItemEntity {

    private int id;
    private int member_id;

    private int product_id;

    public CartItemEntity(int id, int member_id, int product_id) {
        this.id = id;
        this.member_id = member_id;
        this.product_id = product_id;
    }

    public CartItemEntity(int member_id, int product_id) {
        this.member_id = member_id;
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
