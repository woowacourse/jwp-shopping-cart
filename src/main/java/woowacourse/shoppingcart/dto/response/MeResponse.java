package woowacourse.shoppingcart.dto.response;

import java.util.Objects;
import woowacourse.shoppingcart.domain.Customer;

public class MeResponse {

    private String username;
    private String nickname;
    private int age ;

    public MeResponse() {
    }

    public MeResponse(String username, String nickname, int age) {
        this.username = username;
        this.nickname = nickname;
        this.age = age;
    }

    public MeResponse(Customer customer) {
        this(customer.getUsername().getValue(), customer.getNickname().getValue(), customer.getAge().getValue());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MeResponse that = (MeResponse) o;
        return age == that.age
                && Objects.equals(username, that.username)
                && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, nickname, age);
    }
}
