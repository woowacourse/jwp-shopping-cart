package cart.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;

public class ProductRequestDto {

    @NotBlank
    private String name;
    @URL
    private String imgUrl;
    @NumberFormat
    private int price;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, String imgUrl, int price) {

        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price;
    }
}
