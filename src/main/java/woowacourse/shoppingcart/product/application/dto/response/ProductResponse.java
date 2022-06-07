package woowacourse.shoppingcart.product.application.dto.response;

import woowacourse.shoppingcart.product.domain.Product;

public class ProductResponse {

    private long productId;
    private String name;
    private int price;
    private String image;

    public ProductResponse() {
    }

    public ProductResponse(final long productId, final String name, final int price, final String image) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static ProductResponse of(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
