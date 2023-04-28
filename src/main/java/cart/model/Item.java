package cart.model;

import java.math.BigInteger;

public class Item {

    private final ItemName itemName;
    private final ItemUrl itemUrl;
    private final ItemPrice itemPrice;

    public Item(String name, String imageUrl, int price) {
        itemName = new ItemName(name);
        itemUrl = new ItemUrl(imageUrl);
        itemPrice = new ItemPrice(BigInteger.valueOf(price));
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
