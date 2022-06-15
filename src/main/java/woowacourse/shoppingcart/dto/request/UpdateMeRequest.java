package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateMeRequest {

    @NotBlank
    @Size(min = 1, max = 10)
    private String nickname;
    @Min(value = 0)
    private int age;

    public UpdateMeRequest() {
    }

    public UpdateMeRequest(String nickname, int age) {
        this.nickname = nickname;
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
