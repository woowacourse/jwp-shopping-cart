package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String image;

    public OrderDetail() {
    }

    public OrderDetail(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderDetail(final Product product, final int quantity) {
        this(product.getId(), product.getPrice(), product.getName(), product.getImage(), quantity);
    }

    public OrderDetail(final Long productId, final int price, final String name,
                       final String image, final int quantity) {
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
    }

    public int calculatePrice() {
        return price * quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }
}
