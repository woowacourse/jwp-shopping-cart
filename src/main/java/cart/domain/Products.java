package cart.domain;

import java.util.List;
import java.util.NoSuchElementException;

public class Products {

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public void delete(final Product productToDelete) {
        Product product = products.stream()
                .filter(productInProducts -> productInProducts.equals(productToDelete))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("삭제하려는 상품이 없습니다"));
        products.remove(product);
    }
}
