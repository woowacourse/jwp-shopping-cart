package cart.dto;

import cart.entity.Product;

public class ProductDto {
    private final int id;
    private final String name;
    private final int price;
    private final String image;

    private ProductDto(int id, String name, int price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static ProductDto from(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImage());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
