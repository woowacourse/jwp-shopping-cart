package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.product.Product;

public class ProductRequest {
    private final String name;
    private final Integer price;
    private final String imageUrl;
    private final Boolean deleted;

    public ProductRequest() {
        this(null, null, null, null);
    }

    public ProductRequest(String name, Integer price, String imageUrl, Boolean deleted) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.deleted = deleted;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public Product toProduct() {
        return new Product(name, price, imageUrl, deleted);
    }
}
