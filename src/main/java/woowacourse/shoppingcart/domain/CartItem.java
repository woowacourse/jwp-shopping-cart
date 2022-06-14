package woowacourse.shoppingcart.domain;

public class CartItem {

    private Long id;
    private Long customerId;
    private Product product;
    private Long quantity;
    private boolean checked;

    private CartItem() {
    }

    public CartItem(Long id, Long customerId, Product product, Long quantity, boolean checked) {
        validateNull(product, quantity);
        validatePositive(quantity);
        this.id = id;
        this.customerId = customerId;
        this.product = product;
        this.quantity = quantity;
        this.checked = checked;
    }

    private void validateNull(Product product, Long quantity) {
        if (product == null || quantity == null) {
            throw new IllegalArgumentException("상품과 수량에는 null이 들어올 수 없습니다.");
        }
    }

    private void validatePositive(Long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량에는 음수가 들어올 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Product getProduct() {
        return product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}
