package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.exception.InvalidProductPropertyException;

public class Product {

    private Long id;
    private Name name;
    private Price price;
    private String imageUrl;
    private Amount quantity;

    private Product() {
    }

    public Product(Long id, String name, int price, String imageUrl, int quantity) {
        this.id = id;
        this.imageUrl = imageUrl;
        try {
            this.name = new Name(name);
            this.price = new Price(price);
            this.quantity = new Amount(quantity);
        } catch (IllegalArgumentException e) {
            throw new InvalidProductPropertyException(e.getMessage());
        }
    }

    public Product(String name, int price, String imageUrl, int quantity) {
        this(null, name, price, imageUrl, quantity);
    }

    boolean isSameId(Long productId) {
        return id.equals(productId);
    }

    public String getName() {
        return name.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}
