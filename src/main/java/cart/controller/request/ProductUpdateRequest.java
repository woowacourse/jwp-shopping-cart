package cart.controller.request;

public class ProductUpdateRequest {
	private String name;
	private double price;
	private String image;

	public ProductUpdateRequest() {
	}

	public ProductUpdateRequest(String name, double price, String image) {
		this.name = name;
		this.price = price;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}
}
