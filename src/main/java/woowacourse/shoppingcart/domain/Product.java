package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidProductException;

public class Product {
    private final Long id;
    private final String name;
    private final int price;
    private final int stock;
    private final String imageUrl;

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

    public Product(final Product product, final int stock) {
        this(product.getId(), product.getName(), product.getPrice(), stock, product.getImageUrl());
    }

    public Product purchaseProduct(final int purchaseQuantity) {
        if (stock < purchaseQuantity) {
            throw new InvalidProductException("제품의 수량보다 더 주문할 수 없습니다.");
        }
        return new Product(this, stock - purchaseQuantity);
    }

    public Long getId() {
        return id;
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
}
