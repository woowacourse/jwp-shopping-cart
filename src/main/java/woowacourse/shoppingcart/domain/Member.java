package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.domain.value.Email;
import woowacourse.shoppingcart.domain.value.Nickname;
import woowacourse.shoppingcart.domain.value.Password;

public class Member {

    private final Long id;
    private final Email email;
    private final Password password;
    private final Nickname nickname;

    public Member(String email, String password, String nickname) {
        this(null, email, password, nickname);
    }

    public Member(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = new Email(email);
        this.password = new Password(password);
        this.nickname = new Nickname(nickname);
    }

    public boolean matchPassword(String password) {
        return this.password.isSameValue(password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getNickname() {
        return nickname.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(email, member.email)
                && Objects.equals(password, member.password) && Objects.equals(nickname,
                member.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, nickname);
    }
}
