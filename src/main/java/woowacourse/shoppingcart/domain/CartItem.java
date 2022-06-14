package woowacourse.shoppingcart.domain;

public class CartItem {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private int quantity;
    private String imageUrl;

    public CartItem() {
    }

    public CartItem(final Long id, final Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public CartItem(final Long id, final Long productId, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public CartItem(Long id, Long productId, String name, int price, int quantity, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
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

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void updateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 음수로 지정할 수 없습니다.");
        }

        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CartItem cartItem = (CartItem)o;

        if (getPrice() != cartItem.getPrice())
            return false;
        if (getQuantity() != cartItem.getQuantity())
            return false;
        if (getId() != null ? !getId().equals(cartItem.getId()) : cartItem.getId() != null)
            return false;
        if (getProductId() != null ? !getProductId().equals(cartItem.getProductId()) : cartItem.getProductId() != null)
            return false;
        if (getName() != null ? !getName().equals(cartItem.getName()) : cartItem.getName() != null)
            return false;
        return getImageUrl() != null ? getImageUrl().equals(cartItem.getImageUrl()) : cartItem.getImageUrl() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getProductId() != null ? getProductId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getPrice();
        result = 31 * result + getQuantity();
        result = 31 * result + (getImageUrl() != null ? getImageUrl().hashCode() : 0);
        return result;
    }
}
