package cart.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductModifyRequest {

    @NotBlank
    private String name;

    @Positive
    private int price;

    @NotBlank
    private String imageUrl;

    public ProductModifyRequest() {
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
}
