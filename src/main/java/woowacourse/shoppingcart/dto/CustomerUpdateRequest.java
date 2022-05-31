package woowacourse.shoppingcart.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerUpdateRequest {

    @NotBlank
    @Length(min = 2, max = 10)
    private String nickname;

    @NotBlank
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{10,}")
    private String password;

    @NotBlank
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{10,}")
    private String newPassword;

    public CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(final String nickname, final String password, final String newPassword) {
        this.nickname = nickname;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
