package cart.dto.response;

import cart.dto.application.ItemEntityDto;
import java.util.Objects;

public class ItemEntityResponse {
    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ItemEntityResponse(final ItemEntityDto item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.imageUrl = item.getImageUrl();
    }

    public long getId() {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemEntityResponse that = (ItemEntityResponse) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
