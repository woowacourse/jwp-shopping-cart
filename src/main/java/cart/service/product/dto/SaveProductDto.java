package cart.service.product.dto;

import cart.controller.dto.ProductRequest;

public class SaveProductDto {

	private final String name;
	private final String image;
	private final Integer price;

	public SaveProductDto(final ProductRequest productRequest) {
		this(productRequest.getName(), productRequest.getImage(), productRequest.getPrice());
	}

	public SaveProductDto(final String name, final String image, final Integer price) {
		this.name = name;
		this.image = image;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public String getImage() {
		return image;
	}

	public Integer getPrice() {
		return price;
	}
}
