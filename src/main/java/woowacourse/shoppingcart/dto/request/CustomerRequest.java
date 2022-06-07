package woowacourse.shoppingcart.dto.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CustomerRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Length(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Length(min = 1, max = 10, message = "이름은 1자 이상 10자 이하여야 합니다.")
    private String nickname;


    @Min(value = 0, message = "올바른 나이를 입력해주세요.")
    private int age;

    private CustomerRequest() {
    }

    public CustomerRequest(String username, String password, String nickname, int age) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }
}
