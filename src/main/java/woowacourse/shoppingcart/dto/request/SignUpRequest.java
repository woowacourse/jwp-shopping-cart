package woowacourse.shoppingcart.dto.request;

import woowacourse.shoppingcart.domain.Age;
import woowacourse.shoppingcart.domain.Nickname;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;

public class SignUpRequest {

    private String username;
    private String password;
    private String nickname;
    private Integer age;

    public SignUpRequest() {
    }

    public SignUpRequest(String username, String password, String nickname, int age) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public Username getUsername() {
        return new Username(username);
    }

    public Password getPassword() {
        return new Password(password);
    }

    public Nickname getNickname() {
        return new Nickname(nickname);
    }

    public Age getAge() {
        return new Age(age);
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
}
