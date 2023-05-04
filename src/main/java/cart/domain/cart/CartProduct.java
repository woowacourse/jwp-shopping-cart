package cart.domain.cart;

import cart.domain.product.ImageUrl;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.domain.product.ProductName;

public class CartProduct {

    private final Long cartProductId;
    private final Long productId;
    private final ProductName productName;
    private final ImageUrl imageUrl;
    private final Price price;
    private final ProductCategory category;

    public CartProduct(final Long id, final Product product) {
        this(id, product.getId(), product.getProductNameValue(), product.getImageUrlValue(), product.getPriceValue(), product.getCategory());
    }

    public CartProduct(final Long cartProductId, final Long productId, final String productName, final String imageUrl, final Integer price, final ProductCategory category) {
        this.cartProductId = cartProductId;
        this.productId = productId;
        this.productName = new ProductName(productName);
        this.imageUrl = new ImageUrl(imageUrl);
        this.price = new Price(price);
        this.category = category;
    }

    public Long getCartProductId() {
        return cartProductId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductNameValue() {
        return productName.getName();
    }

    public String getImageUrlValue() {
        return imageUrl.getImageUrl();
    }

    public Integer getPriceValue() {
        return price.getPrice();
    }

    public ProductCategory getCategory() {
        return category;
    }
}
