package cart.business.domain;

import java.util.List;

public class Products {

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product findProductById(Integer id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        throw new IllegalArgumentException("해당 상품은 상품 목록에 없습니다");
    }

    public void remove(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}
