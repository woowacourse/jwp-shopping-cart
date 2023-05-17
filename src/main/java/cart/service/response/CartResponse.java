package cart.service.response;

public class CartResponse {
	private final long id;
	private final String image;
	private final String name;
	private final double price;

	public CartResponse(final long id, final String image, final String name, final double price) {
		this.id = id;
		this.image = image;
		this.name = name;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}
}
