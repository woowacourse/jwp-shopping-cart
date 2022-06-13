package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private String imageUrl;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, String description, Integer price, Integer stock, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock(),
                product.getImageUrl());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
