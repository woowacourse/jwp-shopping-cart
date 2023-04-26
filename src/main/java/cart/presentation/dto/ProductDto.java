package cart.presentation.dto;

public class ProductDto {

    private final Integer id;
    private final String name;
    private final String url;
    private final Integer price;

    public ProductDto(Integer id, String name, String url, Integer price) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPrice() {
        return price;
    }
}
