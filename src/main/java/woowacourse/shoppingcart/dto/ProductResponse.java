package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long id;
    private String thumbnail;
    private String name;
    private Integer price;
    private Integer quantity;

    public ProductResponse() {
    }

    private ProductResponse(Long id, String thumbnail, String name, Integer price, Integer quantity) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static ProductResponse from(Product product, Integer quantity) {
        return new ProductResponse(
                product.getId(), product.getImageUrl(), product.getName(), product.getPrice(), quantity);
    }

    public Long getId() {
        return id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
