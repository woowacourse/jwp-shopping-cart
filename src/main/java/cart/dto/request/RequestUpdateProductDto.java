package cart.dto.request;

import java.util.Optional;

public class RequestUpdateProductDto {

    private final String name;
    private final Integer price;
    private final String image;

    public RequestUpdateProductDto(final String name, final Integer price, final String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Integer> getPrice() {
        return Optional.ofNullable(price);
    }

    public Optional<String> getImage() {
        return Optional.ofNullable(image);
    }
}
