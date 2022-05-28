package woowacourse.auth.domain;

public class Member {

    private final Email email;
    private final Password password;
    private final Nickname nickname;

    public Member(String email, String password, String nickname) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.nickname = new Nickname(nickname);
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
}
