package cart.product.domain;

public class Product {
    
    public static final String EMPTY_PRODUCT_NAME_ERROR = "상품 이름이 없습니다.";
    private long id;
    private String name;
    private String image;
    private int price;
    
    public Product(final long id, final String name, final String image, final int price) {
        validateName(name);
        
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }
    
    private void validateName(final String name) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_PRODUCT_NAME_ERROR);
        }
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
