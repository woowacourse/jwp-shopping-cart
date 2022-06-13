package woowacourse.shoppingcart.dto;

public class CustomerWithId {
    private final Long id;

    public CustomerWithId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isNotLogin() {
        return id == null;
    }
}
