package cart.dto.product;

import cart.domain.product.Product;

public class ResponseProductDto {

    private final Long id;
    private final String name;
    private final String image;
    private final int price;

    private ResponseProductDto(final Long id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }
    
    public static ResponseProductDto create(final Product product) {
        return new ResponseProductDto(product.getId(), product.getName().getValue(), product.getImage(),
                product.getPrice().getValue());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getImage() {
        return image;
    }
    
    public int getPrice() {
        return price;
    }
}
