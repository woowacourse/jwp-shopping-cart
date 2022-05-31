package woowacourse.shoppingcart.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CustomerRegisterRequest {

    @NotNull
    @Email
    private String email;

    @NotBlank
    @Length(min = 2, max = 10)
    private String nickname;

    @NotBlank
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{10,}")
    private String password;

    public CustomerRegisterRequest() {
    }

    public CustomerRegisterRequest(final String email, final String nickname, final String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
