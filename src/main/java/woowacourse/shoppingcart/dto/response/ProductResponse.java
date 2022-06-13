package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private int stock;
    private String imageUrl;

    private ProductResponse() {
    }

    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getStock(), product.getImageUrl());
    }

    public ProductResponse(Long id, String name, int price, int stock, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
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

    public int getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
