package cart.dto.request;

import cart.domain.Product;

import javax.validation.constraints.NotNull;

public class ProductCreateDto {
    @NotNull
    private String name;
    @NotNull
    private String image;
    @NotNull
    private int price;

    public ProductCreateDto() {
    }

    public ProductCreateDto(final String name, final String image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Product toProduct() {
        return new Product(name, image, price);
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
