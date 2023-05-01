package cart.service.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ProductCreateRequest {
	@NotBlank(message = "상품명은 공백일 수 없습니다.")
	@Length(min = 1, max = 20, message = "상품명은 1글자 이상 20글자 이하로 작성해주세요.")
	private String name;

	@PositiveOrZero(message = "금액은 음수일 수 없습니다.")
	private double price;

	@NotNull(message = "이미지 주소를 추가해주세요.")
	private String image;

	public ProductCreateRequest() {
	}

	public ProductCreateRequest(String name, double price, String image) {
		this.name = name;
		this.price = price;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}
}
