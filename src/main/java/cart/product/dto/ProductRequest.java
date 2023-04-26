package cart.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class ProductRequest {
	@NotBlank(message = "상품명을 입력해주세요.")
	@Size(max = 20, message = "{max} 글자 이하만 입력 가능합니다.")
	private final String name;

	@NotNull(message = "상품가격을 입력해주세요.")
	@Range(min = 0, message = "상품 금액은 {min}원 이상의 정수만 입력가능 합니다.")
	private final Integer price;
	private final String image;

	public ProductRequest(String name, Integer price, String image) {
		this.name = name;
		this.price = price;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public Integer getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}
}
