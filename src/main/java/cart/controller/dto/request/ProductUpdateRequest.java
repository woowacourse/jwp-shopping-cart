package cart.controller.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductUpdateRequest {

    @NotBlank(message = "빈 값을 허용하지 않습니다.")
    @Size(max = 32)
    private String name;

    private Integer price;

    @NotBlank(message = "빈 값을 허용하지 않습니다.")
    private String imageUrl;

    public ProductUpdateRequest() {

    }

    public ProductUpdateRequest(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public void setName(String updateName) {
        this.name = updateName;
    }

    public void setPrice(int updatePrice) {
        this.price = updatePrice;
    }
}
