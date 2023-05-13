package cart.business.domain;

import java.util.Objects;


public class Cart {

    private final Integer id;
    private final Integer memberId;
    private final Products products;
    public static int sequence = 1;

    public Cart(Integer memberId, Products products) {
        this.id = sequence++;
        this.memberId = memberId;
        this.products = products;
    }

    public void addProduct(Product product) {
        products.addProduct(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Products getProducts() {
        return products;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(memberId, cart.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

}
