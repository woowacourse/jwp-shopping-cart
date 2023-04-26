package cart.dto;

public class ProductRequestDto {
    private Long id;
    private String name;
    private String image;
    private int price;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, String image, int price) {
        this(null, name, image, price);
    }

    public ProductRequestDto(Long id, String name, String image, int price) {
        this.id = id;
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
