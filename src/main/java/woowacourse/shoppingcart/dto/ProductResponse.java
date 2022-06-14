package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private String thumbnail;

    private ProductResponse() {
    }

    public ProductResponse(Long id, String name, int price, String thumbnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getThumbnail());
    }

    public static ProductResponse of(Long productId, ProductRequest productRequest) {
        return new ProductResponse(
                productId, productRequest.getName(), productRequest.getPrice(), productRequest.getThumbnail()
        );
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
