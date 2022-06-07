package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductResponse(long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static ProductResponse of(final Product product) {
        return new ProductResponse(product.getId(), product.getName().getValue(), product.getPrice(), product.getImageUrl().getValue());
    }
}
