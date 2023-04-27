package cart.domain;

import java.util.Objects;

public class Item {

    private final Long id;
    private final Name name;
    private final ImageUrl imageUrl;
    private final Price price;

    public static class Builder {
        private Long id;
        private Name name;
        private ImageUrl imageUrl;
        private Price price;

        public Builder() {}

        public Builder id(Long value) {
            id = value;
            return this;
        }
        public Builder name(Name value) {
            name = value;
            return this;
        }
        public Builder imageUrl(ImageUrl value) {
            imageUrl = value;
            return this;
        }
        public Builder price(Price value) {
            price = value;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }

    private Item(Builder builder) {
        validate(builder);
        this.id = builder.id;
        this.name =  builder.name;
        this.imageUrl = builder.imageUrl;
        this.price = builder.price;
    }

    private void validate(Builder builder) {
        if (builder.name == null) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        if (builder.price == null) {
            throw new IllegalArgumentException("가격은 빈 값일 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getImageUrl() {
        return imageUrl.getValue();
    }

    public Integer getPrice() {
        return price.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id)
                && Objects.equals(name, item.name)
                && Objects.equals(imageUrl, item.imageUrl)
                && Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, price);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }
}
