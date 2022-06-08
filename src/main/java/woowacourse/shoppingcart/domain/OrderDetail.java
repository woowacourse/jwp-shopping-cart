package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private final Product product;
    private final Quantity quantity;

    public OrderDetail(Product product, Quantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getProductName() {
        return product.getName();
    }

    public int getProductPrice() {
        return product.getPrice();
    }

    public String getProductImageUrl() {
        return product.getImageUrl();
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}
