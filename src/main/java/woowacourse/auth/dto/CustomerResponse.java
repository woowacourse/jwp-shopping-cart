package woowacourse.auth.dto;

public class CustomerResponse {

    private String username;
    private String email;

    public CustomerResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
