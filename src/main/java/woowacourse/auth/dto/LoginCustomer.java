package woowacourse.auth.dto;

public class LoginCustomer {

    private final Long id;

    public LoginCustomer(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
