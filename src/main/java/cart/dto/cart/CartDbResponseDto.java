package cart.dto.cart;

public class CartDbResponseDto {

    private final Long id;
    private final Long memberId;

    private CartDbResponseDto(final Long id, final Long memberId) {
        this.id = id;
        this.memberId = memberId;
    }

    public static CartDbResponseDto from(final Long id, final Long memberId) {
        return new CartDbResponseDto(id, memberId);
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }
}
