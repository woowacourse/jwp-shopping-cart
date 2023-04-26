package cart.product.dto;

import cart.product.domain.Product;

public class ResponseProductDto {
    
    private final String name;
    private final String image;
    private final int price;
    private final long id;
    
    public ResponseProductDto(final long id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }
    
    public static ResponseProductDto create(final Product product) {
        return new ResponseProductDto(product.getId(), product.getName().getValue(), product.getImage(),
                product.getPrice().getValue());
    }
    
    public long getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getImage() {
        return this.image;
    }
    
    public int getPrice() {
        return this.price;
    }
}
