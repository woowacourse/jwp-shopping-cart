package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.product.Product;

public class JsonToProduct {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public JsonToProduct() {
        this(null, null, null, null);
    }

    public JsonToProduct(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public Long getId() {
        return id;
    }
}
