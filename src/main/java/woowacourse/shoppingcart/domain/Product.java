package woowacourse.shoppingcart.domain;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
    private String imageUrl;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final int quantity, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final int price, final int quantity, final String imageUrl) {
        this(null, name, price, quantity, imageUrl);
    }

    public void removeQuantity(int quantity) {
        if (this.quantity - quantity < 0) {
            throw new IllegalArgumentException("현재 수량보다 더 많이 꺼낼 수 없습니다.");
        }
        this.quantity -= quantity;
    }

    public void addQuantity(int quantity) {
        if (quantity < 0) {
            removeQuantity(-quantity);
            return;
        }
        this.quantity += quantity;
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

    public Long getId() {
        return id;
    }
}
