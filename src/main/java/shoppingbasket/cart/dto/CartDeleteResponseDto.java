package shoppingbasket.cart.dto;

public class CartDeleteResponseDto {

    private int deletedRowCount;

    private CartDeleteResponseDto() {
    }

    public CartDeleteResponseDto(final int deletedRowCount) {
        this.deletedRowCount = deletedRowCount;
    }

    public int getDeletedRowCount() {
        return deletedRowCount;
    }
}
