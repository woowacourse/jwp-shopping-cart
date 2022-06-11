package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerSaveRequest {

    private String email;
    private String password;
    private String nickname;

    public CustomerSaveRequest() {

    }

    public CustomerSaveRequest(final String email, final String password, final String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public Customer toEntity() {
        return new Customer(email, password, nickname);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
