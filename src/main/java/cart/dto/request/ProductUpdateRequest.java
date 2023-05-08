package cart.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class ProductUpdateRequest {

    @NotBlank
    private Long id;
    @NotBlank
    private String name;
    @PositiveOrZero(message = "가격은 음수가 될 수 없습니다.")
    private int price;
    @NotBlank
    private String imageUrl;

    public ProductUpdateRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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
