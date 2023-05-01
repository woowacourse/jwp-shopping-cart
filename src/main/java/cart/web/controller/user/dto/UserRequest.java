package cart.web.controller.user.dto;

// TODO: 추후에 로그인 로직시, Validation 처리하도록 구현
public class UserRequest {

    private final String email;
    private final String password;

    public UserRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
