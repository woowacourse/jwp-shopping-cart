package cart.domain.product.entity;

import java.time.LocalDateTime;
import org.springframework.lang.Nullable;

public class Product {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Product(@Nullable final Long id, final String name, final int price,
        final String imageUrl,
        @Nullable final LocalDateTime createdAt, @Nullable final LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
