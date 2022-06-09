package woowacourse.shoppingcart.dto;

public class LookUpUser {
    private final Long id;

    public LookUpUser(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isNotLogin() {
        return id == null;
    }
}
