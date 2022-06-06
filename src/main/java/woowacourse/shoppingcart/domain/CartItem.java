package woowacourse.shoppingcart.domain;

public class CartItem {

    private final Product product;

    public CartItem(Product product) {
        this.product = product;
    }

    public static CartItem of(Long id, String name, int price, String thumbnail) {
        return new CartItem(new Product(id, name, price, thumbnail));
    }

    public Product getProduct() {
        return product;
    }
}
