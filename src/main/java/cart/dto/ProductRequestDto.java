package cart.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ProductRequestDto {

    @Size(min = 1, max = 20, message = "상품 이름은 1~20자 사이어야 합니다.")
    private final String name;

    @Min(value = 100, message = "가격은 100원 이상이어야 합니다.")
    private final int price;

    @URL(message = "이미지는 Url 형식으로 입력해 주어야 합니다.")
    private final String image;

    public ProductRequestDto(String name, int price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
