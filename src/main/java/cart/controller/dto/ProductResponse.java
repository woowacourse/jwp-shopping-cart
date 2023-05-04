package cart.controller.dto;

import cart.service.product.dto.ProductDto;

public class ProductResponse {

	private Long id;
	private String name;
	private String image;
	private Integer price;

	public ProductResponse() {
	}

	public ProductResponse(ProductDto productDto) {
		this(productDto.getId(), productDto.getName(), productDto.getImage(), productDto.getPrice());
	}

	public ProductResponse(Long id, String name, String image, Integer price) {
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
