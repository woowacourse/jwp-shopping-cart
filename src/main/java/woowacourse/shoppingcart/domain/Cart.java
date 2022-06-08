package woowacourse.shoppingcart.domain;

public class Cart {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int stock;
    private int quantity;

    public Cart() {
    }

    public Cart(final Long id, final Product product, final int quantity) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getStock(),
                quantity);
    }

    public Cart(final Long id, final Long productId, final String name, final int price, final String imageUrl,
                final int stock, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return new Product(productId, name, price, imageUrl, stock);
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

    public int getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }
}
