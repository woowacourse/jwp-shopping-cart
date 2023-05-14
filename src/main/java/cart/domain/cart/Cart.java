package cart.domain.cart;

import cart.domain.product.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {

    private final long memberId;
    private final List<Product> products;

    private Cart(long memberId, List<Product> products) {
        this.memberId = memberId;
        this.products = products;
    }

    public static Cart createEmpty(long userId) {
        return new Cart(userId, new ArrayList<>());
    }

    public static Cart createWithProducts(long userId, List<Product> products) {
        return new Cart(userId, new ArrayList<>(products));
    }

    public void add(Product product) {
        this.products.add(product);
    }

    public void delete(Product product) {
        boolean isDeleted = this.products.remove(product);
        if (!isDeleted) {
            throw new IllegalArgumentException("해당 ID의 상품이 장바구니에 존재하지 않습니다.");
        }
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        return memberId == cart.memberId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}
