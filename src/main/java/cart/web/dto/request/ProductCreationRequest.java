package cart.web.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductCreationRequest {
    @NotEmpty
    private final String name;
    @NotNull
    private final Integer price;
    @NotEmpty
    private final String category;
    private final String imageUrl;

    public ProductCreationRequest(final String name, final Integer price, final String category,
                                  final String imageUrl) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
