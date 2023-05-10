package cart.entity;

public class Cart {
    private final int id;

    private final Product product;


    public Cart(int id, Product product) {
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
