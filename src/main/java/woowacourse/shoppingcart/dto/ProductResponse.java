package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    private ProductResponse() {

    }

    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    private ProductResponse(Long id, String name, Integer price, String imageUrl) {
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

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
