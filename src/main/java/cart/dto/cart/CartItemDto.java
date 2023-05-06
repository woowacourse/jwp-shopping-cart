package cart.dto.cart;

import cart.dto.product.ProductDto;
import cart.entity.ProductEntity;

import java.util.Objects;

public class CartItemDto {

    private final Long id;
    private final ProductDto productDto;

    private CartItemDto(Long id, ProductDto productDto) {
        this.id = id;
        this.productDto = productDto;
    }

    public static CartItemDto fromCartIdAndProductEntity(Long id, ProductEntity entity) {
        return new CartItemDto(id, ProductDto.fromEntity(entity));
    }

    public Long getId() {
        return id;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productDto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItemDto that = (CartItemDto) o;
        return Objects.equals(id, that.id) && Objects.equals(productDto, that.productDto);
    }
}
