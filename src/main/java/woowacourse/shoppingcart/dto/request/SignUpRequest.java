package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import woowacourse.shoppingcart.domain.Age;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Nickname;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;

public class SignUpRequest {

    @NotBlank
    @Size(min=4, max=20)
    private String username;
    @NotBlank
    @Size(min=8, max=20)
    private String password;
    @NotBlank
    @Size(min=1, max=10)
    private String nickname;
    @Min(value=0)
    private int age;

    public SignUpRequest() {
    }

    public SignUpRequest(String username, String password, String nickname, int age) {
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Customer toDomain() {
        Username username = new Username(this.username);
        Password password = new Password(this.password);
        Nickname nickname = new Nickname(this.nickname);
        Age age = new Age(this.age);
        return Customer.ofNoId(username, password, nickname, age);
    }
}
