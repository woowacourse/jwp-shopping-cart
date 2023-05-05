package cart.entity;

public class Cart {
    private final Product product;
    private final Long id;

    public Cart(Product product, Long id) {
        this.product = product;
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getName(){
        return product.getName();
    }
    public Integer getPrice(){
        return product.getPrice();
    }
    public String getImage(){
        return product.getImage();
    }
}
