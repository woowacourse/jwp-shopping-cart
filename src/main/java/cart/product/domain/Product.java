package cart.product.domain;

import cart.product.dto.OptionalProductDto;
import cart.product.dto.ProductDto;
import java.util.Optional;

public class Product {
    
    private final Name name;
    private final String image;
    private final Price price;
    private long id;
    
    public Product(final Name name, final String image, final Price price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }
    
    public Product(final long id, final Name name, final String image, final Price price) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.id = id;
    }
    
    public static Product create(final ProductDto productDto) {
        return new Product(new Name(productDto.getName()), productDto.getImage(), new Price(productDto.getPrice()));
    }
    
    public Product update(final OptionalProductDto optionalProductDto) {
        final Optional<String> optionalName = optionalProductDto.getName();
        final Optional<String> optionalImage = optionalProductDto.getImage();
        final Optional<Integer> optionalPrice = optionalProductDto.getPrice();
        
        String updatedName = this.name.getValue();
        String updatedImage = this.image;
        int updatedPrice = this.price.getValue();
        
        if (optionalName.isPresent()) {
            updatedName = optionalName.get();
        }
        if (optionalImage.isPresent()) {
            updatedImage = optionalImage.get();
        }
        if (optionalPrice.isPresent()) {
            updatedPrice = optionalPrice.get();
        }
        
        return new Product(new Name(updatedName), updatedImage, new Price(updatedPrice));
    }
    
    public long getId() {
        return this.id;
    }
    
    public Name getName() {
        return this.name;
    }
    
    public String getImage() {
        return this.image;
    }
    
    public Price getPrice() {
        return this.price;
    }
}
