package woowacourse.shoppingcart.product.dto;

import java.util.Objects;
import woowacourse.shoppingcart.product.domain.Product;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    private ProductResponse(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductResponse from(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
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

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductResponse that = (ProductResponse) o;
        return price == that.price && Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageUrl);
    }
}
