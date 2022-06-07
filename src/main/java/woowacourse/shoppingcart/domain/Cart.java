package woowacourse.shoppingcart.domain;

public class Cart {

    private Long id;
    private Long productId;
    private String name;
    private Integer price;
    private String imageUrl;
    private int quantity;
    private boolean checked;

    public Cart() {
    }

    public Cart(Long id, Long productId, String name, Integer price, String imageUrl, int quantity, boolean checked) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Cart(Long id, int quantity, boolean checked) {
        this(id, null, null, null, null, quantity, checked);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}
