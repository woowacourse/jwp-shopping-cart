package cart.domain.cart;

import cart.domain.member.MemberId;
import cart.domain.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private CartId id;
    private MemberId memberId;
    private List<Product> products = new ArrayList<>();

    public Cart(final CartId id, final MemberId memberId, final List<Product> products) {
        this(memberId);
        this.id = id;
        this.products = products;
    }

    public Cart(final MemberId memberId) {
        this.memberId = memberId;
    }

    public CartId getId() {
        return id;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
