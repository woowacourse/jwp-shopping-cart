package woowacourse.shoppingcart.domain;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer stock;
    private String imageUrl;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final int stock, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final int price, final int stock, final String imageUrl) {
        this(null, name, price, stock, imageUrl);
    }

    public void removeStock(int stock) {
        if (this.stock - stock < 0) {
            throw new IllegalArgumentException("현재 수량보다 더 많이 꺼낼 수 없습니다.");
        }
        this.stock -= stock;
    }

    public void addStock(int stock) {
        if (stock < 0) {
            removeStock(-stock);
            return;
        }
        this.stock += stock;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }
}
