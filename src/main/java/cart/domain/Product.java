package cart.domain;

import cart.dto.ProductCreationDto;
import cart.dto.ProductUpdateDto;
import cart.entity.ProductEntity;

import java.util.Objects;

public class Product {
    private final Long id;
    private final String name;
    private final ProductImage image;
    private final ProductPrice price;

    public Product(final Long id, final String name, final ProductImage image, final ProductPrice price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static Product from(final ProductCreationDto productCreationDto) {
        return new Product(null, productCreationDto.getName(), new ProductImage(productCreationDto.getImage()), new ProductPrice(productCreationDto.getPrice()));
    }

    public static Product from(final ProductUpdateDto productUpdateDto) {
        return new Product(Long.valueOf(productUpdateDto.getId()), productUpdateDto.getName(), new ProductImage(productUpdateDto.getImage()), new ProductPrice(productUpdateDto.getPrice()));
    }

    public static ProductEntity from(final Product product) {
        return new ProductEntity(product.getName(), product.getImage(), product.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image.getImage();
    }

    public Long getPrice() {
        return price.getPrice();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
