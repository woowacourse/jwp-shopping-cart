package woowacourse.shoppingcart.dto;

public class CustomerDto {

    private final long id;

    public CustomerDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
