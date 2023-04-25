package cart.dto;

public class ProductDto {
    private final int id;
    private final String image;
    private final String name;
    private final int price;

    public ProductDto(final int id, final String image, final String name, final int price) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
