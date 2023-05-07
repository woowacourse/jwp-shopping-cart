package cart.dto.application;

import java.util.Objects;

public class ItemEntityDto {

    private final long id;
    private final ProductDto product;

    public ItemEntityDto(final long id, final ProductEntityDto product) {
        this.id = id;
        this.product = product.getProduct();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemEntityDto that = (ItemEntityDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
