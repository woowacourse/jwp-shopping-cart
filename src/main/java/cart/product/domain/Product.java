package cart.product.domain;

import cart.product.dto.ProductDto;

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
    
    public Product update(final ProductDto productDto) {
        final Name name = new Name(productDto.getName());
        final Price price = new Price(productDto.getPrice());
        return new Product(this.id, name, productDto.getImage(), price);
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
