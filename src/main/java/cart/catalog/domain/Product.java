package cart.catalog.domain;

import cart.catalog.dto.ProductRequestDTO;

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
    
    public static Product create(final ProductRequestDTO productRequestDTO) {
        return new Product(new Name(productRequestDTO.getName()), productRequestDTO.getImage(), new Price(
                productRequestDTO.getPrice()));
    }
    
    public Product update(final ProductRequestDTO productRequestDTO) {
        final Name name = new Name(productRequestDTO.getName());
        final Price price = new Price(productRequestDTO.getPrice());
        return new Product(this.id, name, productRequestDTO.getImage(), price);
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
