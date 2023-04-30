package cart.controller.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ProductCreateRequest {

	@NotEmpty(message = "Null을 허용하지 않습니다.")
	@Size(max = 32)
	private String name;

	private Integer price;

	@NotEmpty(message = "Null을 허용하지 않습니다.")
	private String imageUrl;

	public ProductCreateRequest () {

	}

	public ProductCreateRequest (String name, Integer price, String imageUrl) {
		this.name = name;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public String getName () {
		return name;
	}

	public int getPrice () {
		return price;
	}

	public String getImageUrl () {
		return imageUrl;
	}

	public void setName (String name) {
		this.name = name;
	}

	public void setPrice (int price) {
		this.price = price;
	}

	public void setImageUrl (String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
