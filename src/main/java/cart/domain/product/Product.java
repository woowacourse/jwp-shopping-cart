package cart.domain.product;

import java.time.LocalDateTime;

import cart.service.product.dto.SaveProductDto;
import cart.service.product.dto.UpdateProductDto;

public class Product {

	private Long id;
	private String name;
	private String image;
	private Integer price;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Product(final SaveProductDto saveProductDto) {
		this(null, saveProductDto.getName(), saveProductDto.getImage(), saveProductDto.getPrice(), null, null);
	}

	public Product(final UpdateProductDto updateProductDto) {
		this(updateProductDto.getId(), updateProductDto.getName(), updateProductDto.getImage(),
			updateProductDto.getPrice(), null, null);
	}

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

	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public void setPrice(final Integer price) {
		this.price = price;
	}

	public void setCreatedAt(final LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(final LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
