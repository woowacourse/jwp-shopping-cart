package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.product.Product;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;
    private final Boolean selling;
    private final String description;

    private ProductResponse() {
        this(null, null, null, null, null, null);
    }

    public ProductResponse(Long id, String name, Integer price, String imageUrl, Boolean selling,
            String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.selling = selling;
        this.description = description;
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

    public Boolean getSelling() {
        return selling;
    }

    public String getDescription() {
        return description;
    }
}
