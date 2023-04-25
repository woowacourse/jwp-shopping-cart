package cart.dto;

public class ProductDto {
    private final Long id;
    private final String name;
    private final int price;
    private final String image;

    private ProductDto(final Long id, final String name, final int price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static ProductDto createProductDto(final String name, final int price, final String image){
        return new ProductDto(null, name, price, image);
    }

    public static ProductDto createProductDto(final long id,final String name, final int price, final String image){
        return new ProductDto(id,name,price,image);
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

    public Long getId() {
        return id;
    }
}
