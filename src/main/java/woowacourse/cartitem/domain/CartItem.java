package woowacourse.cartitem.domain;

import woowacourse.product.domain.Price;
import woowacourse.product.domain.Product;

public class CartItem {

    private Long id;
    private Long productId;
    private String name;
    private Price price;
    private String imageURL;
    private Quantity quantity;

    public CartItem(final Long id, final Long productId, final String name, final Price price,
        final String imageURL,
        final Quantity quantity
    ) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.quantity = quantity;
    }

    public CartItem(final Long id, final Product product, final Quantity quantity) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageURL(), quantity);
    }

    public void updateQuantity(final int value) {
        quantity = quantity.update(value);
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

    public Price getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
