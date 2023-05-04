package cart.entity;

public class Cart {
    private int productId;

    private String email;


    public Cart(int productId, String email) {
        this.productId = productId;
        this.email = email;
    }

    public int getProductId() {
        return productId;
    }

    public String getEmail() {
        return email;
    }
}
