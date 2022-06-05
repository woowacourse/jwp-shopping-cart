package woowacourse.customer.dto;

public class ConfirmPasswordRequest {

    private String password;

    public ConfirmPasswordRequest() {
    }

    public ConfirmPasswordRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
