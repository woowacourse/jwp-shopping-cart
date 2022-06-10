package woowacourse.shoppingcart.domain;

public class OrdersDetail {

    private final Long id;
    private final Product product;
    private final int count;

    public OrdersDetail(final Long id, final Product product, final int count) {
        this.id = id;
        this.product = product;
        this.count = count;
    }

    public static OrdersDetail createWithoutId(final Product product, final int count) {
        return new OrdersDetail(null, product, count);
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public Long getProductId() {
        return product.getId();
    }
}
