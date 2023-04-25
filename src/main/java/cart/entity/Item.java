package cart.entity;

public class Item {

    public static final Long INSERT_ID = null;
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public Item(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Item(final String name, final String imageUrl, final int price) {
        this.id = INSERT_ID;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
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
}
