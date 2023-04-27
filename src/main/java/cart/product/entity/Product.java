package cart.product.entity;

import java.time.LocalDateTime;

public class Product {

	private final Long id;
	private final String name;
	private final String image;
	private final Integer price;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public Product(Long id, String name, String image, Integer price, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.price = price;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
