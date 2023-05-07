package cart.dto.entity;

public class CartEntity {
    public static final int DEFAULT_CONT = 1;

    private int id;
    private int count;
    private int member_id;
    private int product_id;

    public CartEntity() {
    }

    public CartEntity(int member_id, int product_id) {
        this.member_id = member_id;
        this.product_id = product_id;
        count = DEFAULT_CONT;
    }

    public CartEntity(int id, int count, int member_id, int product_id) {
        this.id = id;
        this.count = count;
        this.member_id = member_id;
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public int getMember_id() {
        return member_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
