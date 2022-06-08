package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.exception.InvalidCartItemPropertyException;

public class CartItem {

    private Long id;
    private Long productId;
    private Name name;
    private Price price;
    private String imageUrl;
    private Amount quantity;
    private Amount count;

    private CartItem() {
    }

    public CartItem(Long id, int count, Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getQuantity(),
            count);
    }

    public CartItem(Long id, Long productId, String name, int price, String imageUrl,
        int quantity, int count) {
        this.id = id;
        this.productId = productId;
        this.imageUrl = imageUrl;
        try {
            this.name = new Name(name);
            this.price = new Price(price);
            this.quantity = new Amount(quantity);
            this.count = new Amount(count);
        } catch (IllegalArgumentException e) {
            throw new InvalidCartItemPropertyException(e.getMessage());
        }
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity.getValue();
    }

    public int getCount() {
        return count.getValue();
    }
}
