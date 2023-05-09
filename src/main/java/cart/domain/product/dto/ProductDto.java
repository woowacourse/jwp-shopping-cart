package cart.domain.product.dto;

import cart.domain.product.entity.Product;
import java.time.LocalDateTime;
import java.util.Objects;

public class ProductDto {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ProductDto(final Long id, final String name, final int price, final String imageUrl,
        final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductDto of(final Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getCreatedAt(), product.getUpdatedAt());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductDto that = (ProductDto) o;
        return getPrice() == that.getPrice() && Objects.equals(getId(), that.getId())
            && Objects.equals(getName(), that.getName()) && Objects.equals(
            getImageUrl(), that.getImageUrl()) && Objects.equals(getCreatedAt(),
            that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getImageUrl(), getCreatedAt(),
            getUpdatedAt());
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
