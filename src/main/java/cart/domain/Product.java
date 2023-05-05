package cart.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final Id id;
    private final ProductName name;
    private final ImageUrl imageUrl;
    private final Price price;

    public Product(ProductName name, ImageUrl imageUrl, Price price) {
        this(Id.EMPTY_ID, name, imageUrl, price);
    }

    public Product(Id id, ProductName name, ImageUrl imageUrl, Price price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    //todo 질문 : getter를 통해 vo(필드 속성값)이 아닌, vo내부의 원시값을 반환하는 게 맞을까요?
    // 원래는 객체를 반환하였다가, 엔티티를 생성할 때 Product.getId().getId()를 해야해서 이와 같이 바꾸었습니다.
    public Long getId() {
        return id.getId();
    }

    public String getName() {
        return name.getName();
    }

    public String getImageUrl() {
        return imageUrl.getImageUrl();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
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
