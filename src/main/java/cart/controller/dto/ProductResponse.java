package cart.controller.dto;

import cart.domain.product.Product;

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

	public ProductResponse(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.image = product.getImage();
		this.price = product.getPrice();
	}

	public ProductResponse(Long id, Product product) {
		this.id = id;
		this.name = product.getName();
		this.image = product.getImage();
		this.price = product.getPrice();
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
