package woowacourse.shoppingcart.dto;

import java.util.Objects;
import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductResponse(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductResponse that = (ProductResponse) o;
        return price == that.price && Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageUrl);
    }
}
