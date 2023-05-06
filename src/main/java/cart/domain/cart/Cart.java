package cart.domain.cart;

import cart.domain.product.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {

    private final long id;
    private final List<Product> products;

    private Cart(long id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    public static Cart createEmpty(long id) {
        return new Cart(id, new ArrayList<>());
    }

    public static Cart createWithProducts(long id, List<Product> products) {
        return new Cart(id, new ArrayList<>(products));
    }

    public void add(Product product) {
        this.products.add(product);
    }

    public void deleteProductById(long id) {
        for (Product product : this.products) {
            if (product.getId() == id) {
                this.products.remove(product);
                return;
            }
        }
        throw new IllegalArgumentException("해당 ID의 상품이 장바구니에 존재하지 않습니다.");
    }

    public Long getId() {
        return id;
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
        return id == cart.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
