package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    private ProductResponse(long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public long getId() {
        return id;
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
}
