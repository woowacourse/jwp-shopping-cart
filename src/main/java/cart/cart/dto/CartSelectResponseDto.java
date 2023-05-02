package cart.cart.dto;

public class CartSelectResponseDto {
    private int id;
    private String productName;
    private int productPrice;
    private String productImage;

    public CartSelectResponseDto() {
    }

    public CartSelectResponseDto(final int id,
                                 final String productName,
                                 final int productPrice,
                                 final String productImage) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}
