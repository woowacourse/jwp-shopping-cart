package woowacourse.shoppingcart.domain;

public class Carts {

    private Long id;
    private Long memberId;
    private Product product;
    private int quantity;

    public Carts(final Long memberId, final Product product, final int quantity) {
        this(null, memberId, product, quantity);
    }

    public Carts(final Long id, final Long memberId, final Product product, final int quantity) {
        this.id = id;
        this.memberId = memberId;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int calculateTotalPrice() {
        return product.getPrice() * quantity;
    }
}
