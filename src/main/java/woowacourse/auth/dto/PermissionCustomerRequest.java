package woowacourse.auth.dto;

public class PermissionCustomerRequest {

    private String email;

    public PermissionCustomerRequest() {
    }

    public PermissionCustomerRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
