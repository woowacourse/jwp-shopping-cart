package cart.domain;

public class CartData {

    private final Long id;
    private final Name name;
    private final ImageUrl imageUrl;
    private final Price price;

    public CartData(Long id, Name name, ImageUrl imageUrl, Price price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getImageUrl() {
        return imageUrl.getValue();
    }

    public Integer getPrice() {
        return price.getValue();
    }
}
