package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerSaveRequest {

    private String email;
    private String password;
    private String nickname;

    public CustomerSaveRequest() {

    }

    public CustomerSaveRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public Customer toEntity(String encoderPassword) {
        return new Customer(email, encoderPassword, nickname);
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
