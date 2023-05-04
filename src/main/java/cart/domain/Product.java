package cart.domain;

import java.util.Objects;

public class Product {

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_PRICE = 1_000_000;

    private final Integer id;
    private final String name;
    private final String image;
    private final Long price;

    public Product(String name, String image, Long price) {
        this(null, name, image, price);
    }

    public Product(Integer id, String name, String image, Long price) {
        validateName(name);
        validatePrice(price);
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    private void validateName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 " + MAX_NAME_LENGTH + "글자 이하입니다");
        }
    }

    private void validatePrice(Long price) {
        if (price > MAX_PRICE) {
            throw new IllegalArgumentException("가격은 " + MAX_PRICE + "이하입니다");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Product product = (Product)o;

        if (Objects.isNull(id) && Objects.isNull(product.id)) {
            return super.equals(product);
        }
        if (Objects.isNull(id) || Objects.isNull(product.id)) {
            // 한 쪽이 id가 없다면, 같은 데이터일 수 없다. 현 상황에 한정해 상품들의 저장이 한 번에 이뤄지기 때문이다.
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Long getPrice() {
        return price;
    }
}
