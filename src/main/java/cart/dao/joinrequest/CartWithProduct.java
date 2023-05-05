package cart.dao.joinrequest;

public class CartWithProduct {
    private final Integer memberId;
    private final Integer id;
    private final String name;
    private final String image;
    private final Integer price;

    public CartWithProduct(final Integer memberId, final Integer id, final String name, final String image, final Integer price) {
        this.memberId = memberId;
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
