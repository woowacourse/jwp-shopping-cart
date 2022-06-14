package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private Long id;
    private String name;
    private int cost;
    private int quantity;
    private String imageUrl;

    public OrderDetail() {
    }

    public OrderDetail(final Long id, final int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public OrderDetail(final Product product, final int quantity) {
        this(product.getId(), product.getPrice(), product.getName(), product.getImageUrl(), quantity);
    }

    public OrderDetail(final Long id, final int cost, final String name,
                       final String imageUrl, final int quantity) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}
