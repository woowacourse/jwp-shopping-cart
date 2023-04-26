package cart.controller.dto;

public class ProductResponseDto {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String image;

    public ProductResponseDto(final long id, final String name, final int price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

}
