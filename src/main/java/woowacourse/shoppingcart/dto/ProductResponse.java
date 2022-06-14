package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String thumbnail;
    private int quantity;

    private ProductResponse() {
    }

    public ProductResponse(Product product, int quantity) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), quantity);
    }

    public ProductResponse(Long id, String name, Integer price, String thumbnail, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public int getQuantity() {
        return quantity;
    }
}
