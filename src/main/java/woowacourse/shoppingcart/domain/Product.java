package woowacourse.shoppingcart.domain;

public class Product {

    private static final int PRODUCT_BELOW_PRICE = 0;

    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        validatePrice(price);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private void validatePrice(int price) {
        if (price <= PRODUCT_BELOW_PRICE) {
            throw new IllegalArgumentException("0원 이하의 가격은 상품으로 등록 할 수 없습니다.");
        }
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
