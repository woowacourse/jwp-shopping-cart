package cart.domain;

import java.util.List;

public class Cart {
    private final Member member;
    private final List<Product> products;

    public Cart(Member member, List<Product> products) {
        this.member = member;
        this.products = products;
    }

    public int getProductCount() {
        return products.size();
    }

    public List<Product> getProducts() {
        return products;
    }
}
