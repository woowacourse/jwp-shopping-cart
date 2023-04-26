package cart.domain;

public class Product {
	private final long id;
	private final String name;
	private final double price;
	private final String image;

	public Product(long id, String name, double price, String image) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
	}

	public long getId() {
		return id;
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
