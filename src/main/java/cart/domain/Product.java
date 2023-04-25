package cart.domain;

public class Product {
    //상품 ID, 상품 이름, 상품 이미지, 상품 가격
    private final Long id;
    private final Name name;
    private final String image;
    private final Price price;

    public Product(Long id, Name name, String image, Price price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Product(Name name, String image, Price price) {
        this(null, name, image, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price.getAmount();
    }
}
