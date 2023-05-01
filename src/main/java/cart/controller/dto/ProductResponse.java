package cart.controller.dto;

import cart.service.product.dto.ProductDto;

public class ProductResponse {

	private Long id;
	private String name;
	private String image;
	private Integer price;

	public ProductResponse() {
	}

	public ProductResponse(Long id, String name, String image, Integer price) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.price = price;
	}

	public ProductResponse(ProductDto productDto) {
		this.id = productDto.getId();
		this.name = productDto.getName();
		this.image = productDto.getImage();
		this.price = productDto.getPrice();
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
