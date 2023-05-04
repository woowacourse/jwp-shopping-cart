package cart.dao.product.dto;

import cart.domain.product.Product;

public class ProductUpdateDTO {

	private final Long id;
	private final String name;
	private final String image;
	private final Integer price;

	public ProductUpdateDTO(final Product product) {
		this(null, product.getName(), product.getImage(), product.getPrice());
	}

	public ProductUpdateDTO(final Long id, final String name, final String image, final Integer price) {
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
