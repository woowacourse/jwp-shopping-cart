package cart.service.dto;

public class CartAllProductSearchResponse {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    private CartAllProductSearchResponse() {
    }

    public CartAllProductSearchResponse(final Long id, final String name, final int price,
                                        final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
