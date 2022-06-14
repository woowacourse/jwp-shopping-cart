package woowacourse.shoppingcart.dto;

public class CartHasProductDto {

    private boolean exists;

    public CartHasProductDto(boolean exists) {
        this.exists = exists;
    }

    public CartHasProductDto() {
    }

    public boolean getExists() {
        return exists;
    }
}
