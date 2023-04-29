package cart.dto.response;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String image;

    public ProductResponse() {
    }

    public ProductResponse(final Long id, final String name, final Integer price, final String image) {
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
