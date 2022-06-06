package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.product.Product;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;
    private final boolean deleted;

    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.isDeleted());
    }

    public ProductResponse(Long id, String name, int price, String imageUrl, boolean deleted) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.deleted = deleted;
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

    public boolean isDeleted() {
        return deleted;
    }
}
