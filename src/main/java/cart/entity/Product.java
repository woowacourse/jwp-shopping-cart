package cart.entity;

public class Product {

    private Long id;
    private String name;
    private String imageUrl;
    private int price;

    // TDOO : 완성 후 이 생성자가 꼭 필요한지 확인해보기
    public Product(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }
}
