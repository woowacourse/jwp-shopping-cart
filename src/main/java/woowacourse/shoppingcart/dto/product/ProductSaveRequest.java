package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.Product;

public class ProductSaveRequest {

    private String name;
    private int price;
    private int stock;
    private String imageURL;

    private ProductSaveRequest() {
    }

    public ProductSaveRequest(final String name, final int price, final int stock, final String imageURL) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public Product toProduct() {
        return new Product(name, price, stock, imageURL);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageURL() {
        return imageURL;
    }
}
