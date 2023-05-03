package cart.catalog.domain;

import cart.catalog.dto.RequestProductDto;

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
    
    public static Product create(final RequestProductDto requestProductDto) {
        return new Product(new Name(requestProductDto.getName()), requestProductDto.getImage(), new Price(
                requestProductDto.getPrice()));
    }
    
    public Product update(final RequestProductDto requestProductDto) {
        final Name name = new Name(requestProductDto.getName());
        final Price price = new Price(requestProductDto.getPrice());
        return new Product(this.id, name, requestProductDto.getImage(), price);
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
