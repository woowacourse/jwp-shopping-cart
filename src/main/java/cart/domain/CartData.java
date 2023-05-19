package cart.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartData cartData = (CartData) o;
        return Objects.equals(id, cartData.id) && Objects.equals(name, cartData.name) && Objects.equals(imageUrl, cartData.imageUrl) && Objects.equals(price, cartData.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, price);
    }

    @Override
    public String toString() {
        return "CartData{" +
                "id=" + id +
                ", name=" + name +
                ", imageUrl=" + imageUrl +
                ", price=" + price +
                '}';
    }
}
