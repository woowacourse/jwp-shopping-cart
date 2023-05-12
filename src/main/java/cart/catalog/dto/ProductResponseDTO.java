package cart.catalog.dto;

import cart.catalog.domain.Product;

public class ProductResponseDTO {
    
    private final String name;
    private final String image;
    private final int price;
    private final long id;
    
    public ProductResponseDTO(final long id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }
    
    public static ProductResponseDTO create(final Product product) {
        return new ProductResponseDTO(product.getId(), product.getName().getValue(), product.getImage(),
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
