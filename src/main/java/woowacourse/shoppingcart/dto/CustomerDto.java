package woowacourse.shoppingcart.dto;

public class CustomerDto {

    private Long id;
    private String email;
    private String username;

    private CustomerDto() {
    }

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
