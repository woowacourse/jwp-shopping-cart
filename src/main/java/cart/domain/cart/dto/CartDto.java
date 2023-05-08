package cart.domain.cart.dto;

import cart.domain.cart.entity.Cart;
import cart.domain.member.dto.MemberDto;
import cart.domain.member.entity.Member;
import cart.domain.product.dto.ProductDto;
import cart.domain.product.entity.Product;
import java.time.LocalDateTime;

public class CartDto {

    private final Long id;
    private final ProductDto productDto;
    private final MemberDto memberDto;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CartDto(final Long id, final ProductDto productDto, final MemberDto memberDto,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt) {
        this.id = id;
        this.productDto = productDto;
        this.memberDto = memberDto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CartDto of(final Cart cart) {
        return new CartDto(
            cart.getId(),
            ProductDto.of(cart.getProduct()),
            MemberDto.of(cart.getMember()),
            cart.getCreatedAt(),
            cart.getUpdatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productDto.getName();
    }

    public String getProductImageUrl() {
        return productDto.getImageUrl();
    }

    public int getProductPrice() {
        return productDto.getPrice();
    }
}
