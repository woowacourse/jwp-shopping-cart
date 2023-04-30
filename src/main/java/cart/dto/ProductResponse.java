package cart.dto;

import cart.dao.entity.Product;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String imgUrl;

    public ProductResponse(Long id, String name, Integer price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImgUrl());
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

    public String getImgUrl() {
        return imgUrl;
    }
}
