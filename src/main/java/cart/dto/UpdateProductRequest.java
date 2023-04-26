package cart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UpdateProductRequest {

    private final Long id;
    @NotNull
    private final String name;
    private final String imageUrl;
    @Positive
    private final int price;

    public UpdateProductRequest(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
