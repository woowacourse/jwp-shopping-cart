package woowacourse.shoppingcart.domain;

public class CartItem {

    private final Long id;
    private final Product product;
    private final Integer count;

    public CartItem(final Long id, final Product product, final Integer count) {
        this.id = id;
        this.product = product;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getThumbnailUrl();
    }

    public Integer getQuantity() {
        return product.getQuantity();
    }

    public Integer getCount() {
        return count;
    }
}
