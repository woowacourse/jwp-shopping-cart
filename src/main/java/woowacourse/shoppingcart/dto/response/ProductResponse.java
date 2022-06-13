package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private String thumbnail;

    public ProductResponse() {
    }

    private ProductResponse(Long id, String name, int price, String thumbnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
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

    public String getThumbnail() {
        return thumbnail;
    }
}
