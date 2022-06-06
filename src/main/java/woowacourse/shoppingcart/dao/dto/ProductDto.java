package woowacourse.shoppingcart.dao.dto;

import woowacourse.shoppingcart.domain.product.Product;

public class ProductDto {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductDto(Product product) {
        this(null, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public ProductDto(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }
}
