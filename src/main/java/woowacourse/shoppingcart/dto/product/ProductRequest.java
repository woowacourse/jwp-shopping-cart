package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.product.Product;

public class ProductRequest {
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public ProductRequest() {
        this(null, null, null);
    }

    public ProductRequest( String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public Product toProduct() {
        return new Product(name, price, imageUrl);
    }
}
