package cart.domain;

public class Product {
    private final Long uuid;
    private final String name;
    private final String url;
    private final int price;

    public Product(Long uuid, String name, String url, int price) {
        this.uuid = uuid;
        this.name = name;
        this.url = url;
        this.price = price;
    }

    public Long getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getPrice() {
        return price;
    }

}
