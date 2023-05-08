package cart.dao.cart.dto;

public class CartProductDto {

	private final Long id;
	private final String name;
	private final String image;
	private final Integer price;
	private final Integer quantity;

	public CartProductDto(final Long id, final String name, final String image, final Integer price,
		final Integer quantity) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.price = price;
		this.quantity = quantity;
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

	public Integer getQuantity() {
		return quantity;
	}
}
