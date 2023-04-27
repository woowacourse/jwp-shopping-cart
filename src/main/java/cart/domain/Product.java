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
            throw new IllegalArgumentException("가격은 0원부터 21억원 미만입니다.");
        }
        return price;
    }

    private String validateImgUrl(String imgUrl) {
        if (imgUrl.length() > 8000) {
            throw new IllegalArgumentException("URL은 영문기준 8000자 이하입니다.");
        }
        return imgUrl;
    }

    private String validateName(String name) {
        if (name.length() > 255) {
            throw new IllegalArgumentException("상품명은 영문기준 255자 이하입니다.");
        }
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
