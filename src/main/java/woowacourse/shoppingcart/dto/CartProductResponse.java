package woowacourse.shoppingcart.dto;

public class CartProductResponse {

    private Long id;
    private Long productId;
    private String name;
    private Long price;
    private String imageUrl;
    private Long quantity;
    private boolean checked;

    public CartProductResponse(Long id, Long productId, String name, Long price, String imageUrl, Long quantity, boolean checked) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.checked = checked;
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

    public Long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}
