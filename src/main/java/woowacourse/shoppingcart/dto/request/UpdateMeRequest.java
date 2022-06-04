package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateMeRequest {

    @NotBlank
    @Size(min=4, max=20)
    private String username;
    @NotBlank
    @Size(min=1, max=10)
    private String nickname;
    @Min(value=0)
    private Integer age ;

    public UpdateMeRequest() {
    }

    public UpdateMeRequest(String username, String nickname, int age) {
        this.username = username;
        this.nickname = nickname;
        this.age = age;
    }

    public String getUsername() {
        return username;
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
