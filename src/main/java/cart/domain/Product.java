package cart.domain;

public class Product {
    private final String name;
    private final String imgUrl;
    private final int price;

    public Product(String name, String imgURL, int price) {
        this.name = validateName(name);
        this.imgUrl = validateImgUrl(imgURL);
        this.price = validatePrice(price);
    }

    private String validateName(String name) {
        if (name.length() > 255) {
            throw new IllegalArgumentException("상품명은 영문기준 255자 이하입니다.");
        }
        return name;
    }

    private String validateImgUrl(String imgUrl) {
        if (imgUrl.length() > 8000) {
            throw new IllegalArgumentException("URL은 영문기준 8000자 이하입니다.");
        }
        return imgUrl;
    }

    private int validatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0원부터 21억원 미만입니다.");
        }
        return price;
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
