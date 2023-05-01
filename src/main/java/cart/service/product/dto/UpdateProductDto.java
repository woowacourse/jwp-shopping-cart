package cart.service.product.dto;

import cart.controller.dto.ProductRequest;

public class UpdateProductDto {

	private final Long id;
	private final String name;
	private final String image;
	private final Integer price;

	public UpdateProductDto(final Long id, final ProductRequest productRequest) {
		this(id, productRequest.getName(), productRequest.getImage(), productRequest.getPrice());
	}

	public UpdateProductDto(final Long id, final String name, final String image, final Integer price) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.price = price;
	}

	public Long getId() {
		return id;
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
