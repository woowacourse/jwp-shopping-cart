package woowacourse.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DeleteCustomerRequest {

    private final String password;

    @JsonCreator
    public DeleteCustomerRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
