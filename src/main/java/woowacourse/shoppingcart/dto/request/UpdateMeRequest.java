package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotNull;

public class UpdateMeRequest {

    @NotNull(message = "아이디 입력 필요")
    private String username;
    @NotNull(message = "닉네임 입력 필요")
    private String nickname;
    @NotNull(message = "나이 입력 필요")
    private Integer age;

    public UpdateMeRequest() {
    }

    public UpdateMeRequest(String username, String nickname, Integer age) {
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
