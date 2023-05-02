package cart.cart.dto;

public class CartDeleteResponseDto {

    private int deletedRowCount;

    public CartDeleteResponseDto() {
    }

    public CartDeleteResponseDto(final int deletedRowCount) {
        this.deletedRowCount = deletedRowCount;
    }

    public int getDeletedRowCount() {
        return deletedRowCount;
    }
}
