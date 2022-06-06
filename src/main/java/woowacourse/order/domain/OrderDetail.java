package woowacourse.order.domain;

import woowacourse.cartitem.domain.Quantity;
import woowacourse.product.domain.Price;

public class OrderDetail {

    private Long id;
    private Long productId;
    private String name;
    private Price price;
    private String imageURL;
    private Quantity quantity;

    public OrderDetail(final Long id, final Long productId, final String name, final Price price, final String imageURL,
        final Quantity quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.quantity = quantity;
    }

    public OrderDetail(final Long productId, final String name, final Price price, final String imageURL,
        final Quantity quantity) {
        this(null, productId, name, price, imageURL, quantity);
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
