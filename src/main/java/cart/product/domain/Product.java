package cart.product.domain;

public class Product {
    
    private final long id;
    private final Name name;
    private final String image;
    private final Price price;
    
    public Product(final long id, final Name name, final String image, final Price price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
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
