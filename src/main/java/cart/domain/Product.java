package cart.domain;
public class Product {
    private final Long id;
    private final String name;
    private final String imgUrl;
    private final int price;

    private Product(Long id, String name, String imgURL, int price) {
        this.id = id;
        this.name = validateName(name);
        this.imgUrl = validateImgUrl(imgURL);
        this.price = validatePrice(price);
    }

    public static Product createWithId(Long id, String name, String imgURL, int price) {
        return new Product(id, name, imgURL, price);
    }

    public static Product createWithoutId(String name, String imgURL, int price) {
        return new Product(null, name, imgURL, price);
    }


    private int validatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("상품 가격은 0원 이상입니다.");
        }
        return price;
    }

    private String validateImgUrl(String imgUrl) {
        return imgUrl;
    }

    private String validateName(String name) {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price;
    }
}
