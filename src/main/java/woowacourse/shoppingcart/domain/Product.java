package woowacourse.shoppingcart.domain;

public class Product {

    private final Long id;
    private final String name;
    private final Integer price;
    private Integer stock;
    private final String imageUrl;

    public Product(String name, Integer price, Integer stock, String imageUrl) {
        this(null, name, price, stock, imageUrl);
    }

    public Product(Long id, String name, Integer price, Integer stock, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public void receive(int quantity) {
        validateQuantity(quantity);
        this.stock += quantity;
    }

    public void release(int quantity) {
        validateQuantity(quantity);
        validateStock(quantity);
        this.stock -= quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("상품 수량은 0보다 커야 합니다.");
        }
    }

    public void validateStock(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalArgumentException("재고가 충분하지 않습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
