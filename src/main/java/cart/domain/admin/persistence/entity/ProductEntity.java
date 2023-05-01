package cart.domain.admin.persistence.entity;

import cart.domain.EntityMappingException;

public class ProductEntity {

    private static final int NAME_LENGTH_MAX = 20;
    private static final int URL_LENGTH_MAX = 512;

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductEntity(final Long id, final String name, final int price, final String imageUrl) {
        validateNameLength(name);
        validatePrice(price);
        validateImageUrlLength(imageUrl);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductEntity(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    private void validateNameLength(final String name) {
        if (name.length() > NAME_LENGTH_MAX) {
            throw new EntityMappingException("이름의 길이는 20자 이하여야 합니다.");
        }
    }

    private void validatePrice(final int price) {
        if (price % 10 != 0) {
            throw new EntityMappingException("금액은 10원 단위여야 합니다.");
        }
    }

    private void validateImageUrlLength(final String imageUrl) {
        if (imageUrl.length() > URL_LENGTH_MAX) {
            throw new EntityMappingException("URL의 길이가 너무 깁니다.");
        }
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
