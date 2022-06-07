package woowacourse.shoppingcart.dto.response;

public class CustomerDto {

    private final Long id;
    private final String email;
    private final String username;

    public CustomerDto(final Long id, final String email, final String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
