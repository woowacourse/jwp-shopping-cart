package woowacourse.shoppingcart.product.application.dto.response;

import woowacourse.shoppingcart.product.domain.Product;

public class ProductResponse {

    private long id;
    private String name;
    private long price;
    private String image;

    public ProductResponse() {
    }

    public ProductResponse(final long id, final String name, final long price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static ProductResponse of(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
