package woowacourse.shoppingcart.service.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductCreateServiceRequest {
    private String name;
    private long price;
    private String imageUrl;

    public ProductCreateServiceRequest(final String name, final long price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product toProduct() {
        return new Product(this.name, this.price, this.imageUrl);
    }
}
