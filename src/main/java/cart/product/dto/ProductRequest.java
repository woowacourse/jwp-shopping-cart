package cart.product.dto;

public class ProductRequest {
	private final String name;
	private final Integer price;
	private final String image;

	public ProductRequest(String name, Integer price, String image) {
		this.name = name;
		this.price = price;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public Integer getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}

	@Override
	public String toString() {
		return "ProductRequest{" +
			"name='" + name + '\'' +
			", price=" + price +
			", image='" + image + '\'' +
			'}';
	}
}
