package cart.controller.dto.request;


import javax.validation.constraints.NotEmpty;

public class ProductUpdateRequest {

    @NotEmpty(message = "Null을 허용하지 않습니다.")
    private String name;

    private Integer price;

    @NotEmpty(message = "Null을 허용하지 않습니다.")
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

}
