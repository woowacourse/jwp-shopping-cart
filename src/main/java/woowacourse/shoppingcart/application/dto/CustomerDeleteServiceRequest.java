package woowacourse.shoppingcart.application.dto;

public class CustomerDeleteServiceRequest {

    private final Long id;
    private final String password;

    public CustomerDeleteServiceRequest(final Long id, final String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
