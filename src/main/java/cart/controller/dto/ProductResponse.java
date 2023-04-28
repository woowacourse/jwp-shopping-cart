package cart.controller.dto;

import cart.domain.product.Product;

public class ProductResponse {

	private final Long id;
	private final String name;
	private final String image;
	private final Integer price;

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
