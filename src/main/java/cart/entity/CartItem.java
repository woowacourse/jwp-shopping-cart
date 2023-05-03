package cart.entity;

public class CartItem {

    private final int id;
    private final Product product;

    public CartItem(int id, Product product) {
        this.id = id;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

}
