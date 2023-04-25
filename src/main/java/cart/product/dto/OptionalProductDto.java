package cart.product.dto;

import java.util.Optional;

public class OptionalProductDto {
    
    private final Optional<String> name;
    private final Optional<String> image;
    private final Optional<Integer> price;
    
    public OptionalProductDto(final String name, final String image,
            final Integer price) {
        this.name = Optional.of(name);
        this.image = Optional.of(image);
        this.price = Optional.of(price);
    }
    
    public Optional<String> getName() {
        return this.name;
    }
    
    public Optional<String> getImage() {
        return this.image;
    }
    
    public Optional<Integer> getPrice() {
        return this.price;
    }
}
