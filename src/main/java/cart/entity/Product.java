package cart.entity;

import java.util.Objects;

public class Product {
    private final Long id;
    private final String name;
    private final String imgUrl;
    private final Integer price;

    public Product(Long id, String name, String imgUrl, Integer price) {
        validateName(name);
        validatePrice(price);
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    private void validateName(String name) {
        if (name.isBlank() || name.length() > 20) {
            throw new IllegalArgumentException("상품명은 20자 이하로 입력해주세요");
        }
    }

    private void validatePrice(Integer price) {
        if (price < 1000) {
            throw new IllegalArgumentException("상품 가격은 1000원 이상이여야 합니다.");
        }
    }

    public Product(String name, String imgUrl, Integer price) {
        this(null, name, imgUrl, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
