package woowacourse.shoppingcart.domain;

public class CartItemInfo {
    private final Product product;

    public CartItemInfo(Product product) {
        this.product = product;
    }

    public static CartItemInfo of(Long id, String name, int price, String thumbnail) {
        return new CartItemInfo(new Product(id, name, price, thumbnail));
    }

    public Product getProduct() {
        return product;
    }
}
