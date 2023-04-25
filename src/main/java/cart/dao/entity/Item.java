package cart.dao.entity;

import java.util.Objects;

public class Item {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    public static class Builder {
        private Long id;
        private String name;
        private String imageUrl;
        private Integer price;

        public Builder() {}

        public Builder id(Long value) {
            id = value;
            return this;
        }
        public Builder name(String value) {
            name = value;
            return this;
        }
        public Builder imageUrl(String value) {
            imageUrl = value;
            return this;
        }
        public Builder price(Integer value) {
            price = value;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }

    private Item(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.imageUrl = builder.imageUrl;
        this.price = builder.price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(imageUrl, item.imageUrl) && Objects.equals(price, item.price);
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
