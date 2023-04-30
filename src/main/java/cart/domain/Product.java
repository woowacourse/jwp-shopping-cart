package cart.domain;

import java.util.Objects;

public class Product {
	private final long id;
	private final String name;
	private final double price;
	private final String image;

	public Product(long id, String name, double price, String image) {
		validate(name, price, image);
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
	}

	private void validate(final String name, final double price, final String image) {
		if (name.isEmpty() || name.length() > 20) {
			throw new IllegalArgumentException("상품명은 1글자 이상 20글자 이하로 작성해주세요.");
		}
		if (price < 0) {
			throw new IllegalArgumentException("금액은 음수일 수 없습니다.");
		}
		if (Objects.isNull(image)) {
			throw new IllegalArgumentException("이미지 주소가 올바르지 않습니다.");
		}
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
