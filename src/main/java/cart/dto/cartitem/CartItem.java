package cart.dto.cartitem;

public class CartItem {

    private final Long id;
    private final Long memberId;
    private final Long productId;
    private final int count;
    private final String name;
    private final String imageUrl;
    private final int price;

    public CartItem(final Long id, final Long memberId, final Long productId, final int count, final String name, final String imageUrl,
        final int price) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.count = count;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
