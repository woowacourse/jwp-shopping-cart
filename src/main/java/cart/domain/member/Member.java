package cart.domain.member;

public class Member {

    private final Nickname nickname;
    private final String email;
    private final Password password;

    public Member(final Nickname nickname, final String email,
                  final Password password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname.getNickname();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password.getPassword();
    }
}
