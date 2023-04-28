package cart.domain;

public class Product {

    private static final int MAX_NAME_LENGTH = 255;

    private final Long productId;
    private final String productName;
    private final ProductImage productImage;
    private final ProductPrice productPrice;

    public Product(final String productName, final String productImage, final int productPrice) {
        this(null, productName, productImage, productPrice);
    }

    public Product(final Long productId, final Product product) {
        this(productId, product.productName, product.productImage, product.productPrice);
    }

    public Product(final Long productId, final String productName, final String productImage, final int productPrice) {
        this(productId, productName, new ProductImage(productImage), new ProductPrice(productPrice));
    }

    public Product(final Long productId, final String productName, final ProductImage productImage,
            final ProductPrice productPrice) {
        validateName(productName);
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    private void validateName(final String productName) {
        if (productName == null) {
            throw new IllegalArgumentException("이름은 null일 수 없습니다.");
        }
        if (productName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 " + MAX_NAME_LENGTH + "자 이하만 가능합니다.");
        }
        if (productName.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }
}
