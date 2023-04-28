package cart.model;

import java.math.BigInteger;

public class Item {

    private Long id;
    private final ItemName itemName;
    private final ItemUrl itemUrl;
    private final ItemPrice itemPrice;

    public Item(Long id, String name, String imageUrl, int price) {
        this.id = id;
        itemName = new ItemName(name);
        itemUrl = new ItemUrl(imageUrl);
        itemPrice = new ItemPrice(BigInteger.valueOf(price));
    }

    public Item(Long id, Item item) {
        this.id = id;
        this.itemName = item.itemName;
        this.itemUrl = item.itemUrl;
        this.itemPrice = item.itemPrice;
    }

    public Item(String name, String imageUrl, int price) {
        this(null, name, imageUrl, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return itemName.getName();
    }

    public String getImageUrl() {
        return itemUrl.getUrl();
    }

    public int getPrice() {
        return itemPrice.getPrice().intValue();
    }
}
