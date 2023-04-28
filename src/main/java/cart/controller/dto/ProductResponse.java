package cart.controller.dto;

import cart.dao.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public static List<ProductResponse> from(List<ProductEntity> entities) {
        return entities.stream().map(
            entity -> new ProductResponse(entity.getId(), entity.getName(), entity.getPrice(),
                entity.getImageUrl())).collect(Collectors.toList());
    }

    public ProductResponse(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
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
}
