package woowacourse.shoppingcart.domain;

public class Product {

    private static final int MINIMUM_SIZE = 1;

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        validateBlank(name, price, imageUrl);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private void validateBlank(String name, int price, String imageUrl) {
        if (name == null || imageUrl == null) {
            throw new IllegalArgumentException("상품명과 이미지는 null 값이 올 수 없습니다.");
        }
        if (name.isBlank() || imageUrl.isBlank()) {
            throw new IllegalArgumentException("상품명과 이미지를 입력해주세요.");
        }
        if (price < MINIMUM_SIZE) {
            throw new IllegalArgumentException("상품의 가격은 양수여야 합니다.");
        }
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
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

    public Long getId() {
        return id;
    }
}
