package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class Cart {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int quantity;

    public Cart(final Long id, final int quantity, final Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
    }

    public Cart(Long id, Long productId, String name, int price, String imageUrl, int quantity) {
        validateQuantity(quantity);
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Cart ofPlusQuantity() {
        return new Cart(id, productId, name, price, imageUrl, quantity + 1);
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidCartItemException();
        }
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}
