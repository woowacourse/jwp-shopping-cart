package cart.dto.response;

import cart.domain.product.dto.ProductDto;
import java.time.LocalDateTime;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponse() {
    }

    public ProductResponse(final Long id, final String name, final int price, final String imageUrl,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductResponse of(final ProductDto productDto) {
        return new ProductResponse(productDto.getId(), productDto.getName(), productDto.getPrice(),
            productDto.getImageUrl(), productDto.getCreatedAt(), productDto.getUpdatedAt());
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
