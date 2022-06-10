package woowacourse.shoppingcart.domain;

public class OrderDetail {
    private static final int MINIMUM_SIZE = 1;

    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String imageUrl;

    public OrderDetail() {
    }

    public OrderDetail(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderDetail(final Product product, final int quantity) {
        this(product.getId(), product.getPrice(), product.getName(), product.getImageUrl(), quantity);
    }

    public OrderDetail(final Long productId, final int price, final String name, final String imageUrl, final int quantity) {
        validateBlank(productId, price, name, imageUrl, quantity);
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    private void validateBlank(Long productId, int price, String name, String imageUrl, int quantity) {
        if (productId == null || name == null || imageUrl == null) {
            throw new IllegalArgumentException("모든 값이 입력되지 않았습니다.");
        }
        if (name.isBlank() || imageUrl.isBlank()) {
            throw new IllegalArgumentException("상품명과 이미지를 첨부해주세요.");
        }
        if (price < MINIMUM_SIZE || quantity < MINIMUM_SIZE) {
            throw new IllegalArgumentException("가격과 수량은 양수이어야 합니다.");
        }
    }

    public Long getProductId() {
        return productId;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}