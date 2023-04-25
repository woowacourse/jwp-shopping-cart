package cart.dto;

public class ProductRequestDto {
    private final String name;
    private final String image;
    private final int price;

    public ProductRequestDto(String name, String image, int price) {
        this.name = name;
        this.image = image;
        this.price = price;
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
