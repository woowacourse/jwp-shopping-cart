package woowacourse.shoppingcart.domain;

public class CartItem {

    private final Long id;
    private final Long accountId;
    private final Product product;
    private final int quantity;

    public CartItem(Product product, int quantity, Long accountId) {
        this(null, accountId, product, quantity);
    }

    public CartItem(Long id, Long accountId, Product product, int quantity) {
        this.id = id;
        this.accountId = accountId;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
