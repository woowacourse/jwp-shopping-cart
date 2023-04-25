package cart.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class ProductCreateRequest {
    @NotBlank
    @Length(min = 1, max = 10)
    private final String name;

    @Positive
    @Max(10_000_000)
    private final int price;

    @NotBlank
    private final String imageUrl;

    public ProductCreateRequest(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
