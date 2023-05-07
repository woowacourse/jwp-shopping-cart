package cart.domain;

import cart.dao.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Cart {

    private final List<Product> products;

    private Cart(List<Product> products) {
        this.products = products;
    }

    public static Cart from(final List<ProductEntity> productEntities) {
        final List<Product> products = productEntities.stream()
                .map(productEntity -> new Product(productEntity.getName(), productEntity.getPrice(), productEntity.getImage()))
                .collect(Collectors.toList());
        return new Cart(products);
    }

    public void addProduct(final Product product) {
        if (products.contains(product)) {
            throw new IllegalArgumentException("카트에 이미 존재하는 상품입니다.");
        }
        products.add(product);
    }

    public void deleteProduct(final Product product) {
        if (!products.contains(product)) {
            throw new IllegalArgumentException("카트에 존재하지 않는 상품입니다.");
        }
        products.remove(product);
    }
}
