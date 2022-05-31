package woowacourse.auth.dto;

public class DeleteCustomerRequest {

    private final String password;

    public DeleteCustomerRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
