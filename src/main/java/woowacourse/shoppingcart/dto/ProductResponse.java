package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.product.Product;

public class ProductResponse {

    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductResponse(long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductResponse of(Product savedProduct) {
        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getImageUrl());
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
