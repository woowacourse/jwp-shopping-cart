package woowacourse.shoppingcart.domain.cart;

import java.util.Objects;

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

    public CartItem(int count, Product product) {
        this(null, count, product);
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

    boolean isProductOf(Product product) {
        return product.isSameId(productId);
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CartItem cartItem = (CartItem)o;
        return Objects.equals(id, cartItem.id) && Objects.equals(productId, cartItem.productId)
            && Objects.equals(name, cartItem.name) && Objects.equals(price, cartItem.price)
            && Objects.equals(imageUrl, cartItem.imageUrl) && Objects.equals(quantity,
            cartItem.quantity) && Objects.equals(count, cartItem.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, price, imageUrl, quantity, count);
    }
}
