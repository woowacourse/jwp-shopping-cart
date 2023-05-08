package cart.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class CartItemDto {

    private Long cartId;
    private Long productId;

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotBlank(message = "이미지 경로는 공백일 수 없습니다.")
    private String imgUrl;

    @PositiveOrZero(message = "가격은 10억 이하의 음이 아닌 정수여야 합니다.")
    @Max(value = 1_000_000_000, message = "가격은 10억 이하의 음이 아닌 정수여야 합니다.")
    private int price;

    public CartItemDto(Long cartId, Long productId, String name, String imgUrl, int price) {
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
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
