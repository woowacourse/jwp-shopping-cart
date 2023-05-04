package cart.domain;

public class Product {
    private final Name name;
    private final ImgUrl imgUrl;
    private final Price price;

    public Product(String name, String imgUrl, int price) {
        this.name = new Name(name);
        this.imgUrl = new ImgUrl(imgUrl);
        this.price = new Price(price);
    }

    public String getName() {
        return name.getName();
    }

    public String getImgUrl() {
        return imgUrl.getImgUrl();
    }

    public int getPrice() {
        return price.getPrice();
    }
}
